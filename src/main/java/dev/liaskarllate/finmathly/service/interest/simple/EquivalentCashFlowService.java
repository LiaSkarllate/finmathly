package dev.liaskarllate.finmathly.service.interest.simple;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.exception.InputNotValidException;
import dev.liaskarllate.finmathly.model.FlowInputOutput;

/**
 * Service class for calculating the missing value of a target flow of a 
 * proposed cash flow to ensure equivalence with the original cash flow.
 */
@Service
public class EquivalentCashFlowService {
    @Autowired
    private AmountFormulaService amountFormulaService;

    /**
     * Applies calculations to determine the required value of the target flow to 
     * balance the original and proposed cash flows.
     *
     * @param originalCashFlows the list of cash flows from the original scheme
     * @param proposedCashFlows	the list of cash flows from the proposed scheme
     * @param targetFlow 		the target flow whose value needs to be determined
     * @param focalTime 		the time reference for adjusting cash flows
     * @param interestRate 		the interest rate used for financial adjustments
     * @return the computed value for the target flow to ensure cash flow equivalence
     */
    public Double calculateRequiredTargetFlowValue(
            List<FlowInputOutput> originalCashFlows,
            List<FlowInputOutput> proposedCashFlows,
            FlowInputOutput targetFlow,
            Double focalTime,
            Double interestRate) {

    	this.validateParametersProvided(
    			originalCashFlows, 
    			proposedCashFlows, 
    			targetFlow, 
    			focalTime, 
    			interestRate);
    	
        return this.calculate(
                originalCashFlows,
                proposedCashFlows,
                targetFlow,
                focalTime,
                interestRate);
    }

    /**
     * Performs the calculation of the the required value of the target flow to 
     * balance the original and proposed cash flows.
     */
    private Double calculate(
            List<FlowInputOutput> originalCashFlows,
            List<FlowInputOutput> proposedCashFlows,
            FlowInputOutput targetFlow,
            Double focalTime,
            Double interestRate) {

        Double adjustedOriginalFlowsValuesSum = this.calculateAdjustedFlowsValuesSum(originalCashFlows, interestRate, focalTime);
        Double adjustedProposedFlowsValuesSum = this.calculateAdjustedFlowsValuesSum(proposedCashFlows, interestRate, focalTime);

        Double netDifference = adjustedOriginalFlowsValuesSum - adjustedProposedFlowsValuesSum;
        Double flowTime = targetFlow.getTime();
        
        Double targetValue = netDifference;

        if (flowTime < focalTime) {
            targetValue = amountFormulaService.applyFormula(netDifference, interestRate, focalTime - flowTime).getAmount();
        } 
        
        if (flowTime > focalTime) {
        	targetValue = amountFormulaService.applyFormula(netDifference, null, interestRate, flowTime - focalTime).getPrincipal();
        }
        
        return targetValue;
    }

    /**
     * Calculates the sum of adjusted cash flow values based on the given interest rate and focal time.
     *
     * @param flows 		the list of cash flows.
     * @param interestRate	the interest rate used for adjustments.
     * @param focalTime 	the reference time for value adjustment.
     * @return the sum of adjusted cash flow values.
     */
    private Double calculateAdjustedFlowsValuesSum(
            List<FlowInputOutput> flows,
            Double interestRate,
            Double focalTime) {
        return flows.stream()
                .mapToDouble(flow -> this.calculateAdjustedFlowValue(flow, interestRate, focalTime))
                .sum();
    }

    /**
     * Calculates the adjusted value of a single cash flow value based on its timing relative to the focal time.
     *
     * @param flow 			the cash flow to adjust
     * @param interestRate 	the interest rate applied
     * @param focalTime 	the focal time used for adjustment
     * @return the adjusted cash flow value
     */
    private Double calculateAdjustedFlowValue(
            FlowInputOutput flow,
            Double interestRate,
            Double focalTime) {

        Double flowTime = flow.getTime();
        Double flowValue = flow.getValue();

        if (flowTime < focalTime) {
            Double timeDelta = focalTime - flowTime;
            return amountFormulaService.applyFormula(flowValue, interestRate, timeDelta).getAmount();
        } else if (flowTime > focalTime) {
            Double timeDelta = flowTime - focalTime;
            return amountFormulaService.applyFormula(flowValue, null, interestRate, timeDelta).getPrincipal();
        } else {
            return flowValue; 
        }
    }

    private void validateParametersProvided(
            List<FlowInputOutput> originalFlows,
            List<FlowInputOutput> proposedFlows,
            FlowInputOutput targetFlow,
            Double focalTime,
            Double interestRate) {

        if (Objects.isNull(originalFlows) || Objects.isNull(proposedFlows) || Objects.isNull(targetFlow)) {
            throw new InputNotValidException("Os esquemas de pagamento e o evento de interesse não podem ser nulos.");
        }

        if (Objects.isNull(focalTime) || focalTime <= 0) {
            throw new InputNotValidException("O tempo focal deve ser maior que zero e não pode ser nulo.");
        }

        if (Objects.isNull(interestRate) || interestRate <= 0) {
            throw new InputNotValidException("A taxa de juros deve ser maior que zero e não pode ser nula.");
        }

        originalFlows.forEach(flow -> this.validateFlow(flow, "evento do esquema original"));
        proposedFlows.forEach(flow -> this.validateFlow(flow, "evento do esquema proposto"));

        this.validateFlow(targetFlow, "evento de interesse");
    }

    private void validateFlow(FlowInputOutput flow, String flowDescription) {
        if (Objects.isNull(flow)) {
            throw new InputNotValidException("Um " + flowDescription + " é nulo.");
        }

        if (Objects.isNull(flow.getValue()) || flow.getValue() <= 0) {
            throw new InputNotValidException("O valor do " + flowDescription + " deve ser maior que zero e não pode ser nulo.");
        }

        if (Objects.isNull(flow.getTime()) || flow.getTime() <= 0) {
            throw new InputNotValidException("O tempo do " + flowDescription + " deve ser maior que zero e não pode ser nulo.");
        }
    }
}
