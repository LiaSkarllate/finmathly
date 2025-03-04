package dev.liaskarllate.finmathly.service.interest.simple;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.exception.InputNotValidException;
import dev.liaskarllate.finmathly.model.FlowInput;

/**
 * Service class for calculating the missing value of a target flow of a proposed cash 
 * flow to ensure equivalence with the original cash flow.
 */
@Service
public class EquivalentCashFlowService {
    @Autowired
    private AmountFormulaService amountFormulaService;
    
    public Double calculateRequiredTargetFlowValue(
            List<FlowInput> originalFlows,
            List<FlowInput> proposedFlows,
            FlowInput targetFlow,
            Double focalTime,
            Double interestRate) {

    	this.validateParametersProvided(
    			originalFlows, 
    			proposedFlows, 
    			targetFlow, 
    			focalTime, 
    			interestRate);
    	
        return this.calculate(
                originalFlows,
                proposedFlows,
                targetFlow,
                focalTime,
                interestRate);
    }

    private Double calculate(
            List<FlowInput> originalFlows,
            List<FlowInput> proposedFlows,
            FlowInput targetFlow,
            Double focalTime,
            Double interestRate) {

        Double adjustedOriginalFlowsValuesSum = this.calculateAdjustedFlowsValuesSum(originalFlows, interestRate, focalTime);
        Double adjustedProposedFlowsValuesSum = this.calculateAdjustedFlowsValuesSum(proposedFlows, interestRate, focalTime);

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

    private Double calculateAdjustedFlowsValuesSum(
            List<FlowInput> flows,
            Double interestRate,
            Double focalTime) {
        return flows.stream()
                .mapToDouble(flow -> this.calculateAdjustedFlowValue(flow, interestRate, focalTime))
                .sum();
    }

    private Double calculateAdjustedFlowValue(
            FlowInput flow,
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
            List<FlowInput> originalFlows,
            List<FlowInput> proposedFlows,
            FlowInput targetFlow,
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

        originalFlows.forEach(flow -> this.validateFlow(flow, "flow from the original ones"));
        proposedFlows.forEach(flow -> this.validateFlow(flow, "flow from the proposed ones"));

        this.validateFlow(targetFlow, "evento de interesse");
    }

    private void validateFlow(FlowInput flow, String flowDescription) {
        if (Objects.isNull(flow)) {
            throw new InputNotValidException("One " + flowDescription + " is null.");
        }

        if (Objects.isNull(flow.getValue()) || flow.getValue() <= 0) {
            throw new InputNotValidException("The value of " + flowDescription + " must be bigger than zero and it can't be null.");
        }

        if (Objects.isNull(flow.getTime()) || flow.getTime() <= 0) {
            throw new InputNotValidException("The time of " + flowDescription + " must be bigger than zero and it can't be null.");
        }
    }
}