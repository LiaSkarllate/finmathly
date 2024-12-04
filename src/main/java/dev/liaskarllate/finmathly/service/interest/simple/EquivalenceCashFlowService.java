package dev.liaskarllate.finmathly.service.interest.simple;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import dev.liaskarllate.finmathly.exception.IllegalExternalParameterException;
import dev.liaskarllate.finmathly.model.FlowInputOutput;

public class EquivalenceCashFlowService {
    @Autowired
    private AmountFormulaService amountFormulaService;

    public Double applyEquivalence(
            List<FlowInputOutput> originalCashFlows,
            List<FlowInputOutput> proposedCashFlows,
            FlowInputOutput flowOfInterest,
            Double focalTime,
            Double interestRate) {

    	this.validateParameters(
    			originalCashFlows, 
    			proposedCashFlows, 
    			flowOfInterest, 
    			focalTime, 
    			interestRate);
    	
        return this.calculate(
                originalCashFlows,
                proposedCashFlows,
                flowOfInterest,
                focalTime,
                interestRate);
    }

    private Double calculate(
            List<FlowInputOutput> originalCashFlows,
            List<FlowInputOutput> proposedCashFlows,
            FlowInputOutput flowOfInterest,
            Double focalTime,
            Double interestRate) {

        Double adjustedOriginalFlowsValuesSum = this.calculatesAdjustedFlowsValuesSum(originalCashFlows, interestRate, focalTime);
        Double adjustedProposedFlowsValuesSum = this.calculatesAdjustedFlowsValuesSum(proposedCashFlows, interestRate, focalTime);

        Double netDifference = adjustedOriginalFlowsValuesSum - adjustedProposedFlowsValuesSum;
        Double eventTime = flowOfInterest.getTime();
        
        Double valueOfInterest = netDifference;

        if (eventTime < focalTime) {
            valueOfInterest = amountFormulaService.applyFormula(netDifference, interestRate, focalTime - eventTime).getAmount();
        } 
        
        if (eventTime > focalTime) {
        	valueOfInterest = amountFormulaService.applyFormula(netDifference, null, interestRate, eventTime - focalTime).getPrincipal();
        }
        
        return valueOfInterest;
    }

    private Double calculatesAdjustedFlowsValuesSum(
            List<FlowInputOutput> flows,
            Double interestRate,
            Double focalTime) {
        return flows.stream()
                .mapToDouble(flow -> this.calculatesAdjustedEventValue(flow, interestRate, focalTime))
                .sum();
    }

    private Double calculatesAdjustedEventValue(
            FlowInputOutput event,
            Double interestRate,
            Double focalTime) {

        Double eventTime = event.getTime();
        Double eventValue = event.getValue();

        if (eventTime < focalTime) {
            Double timeDelta = focalTime - eventTime;
            return amountFormulaService.applyFormula(eventValue, interestRate, timeDelta).getAmount();
        } else if (eventTime > focalTime) {
            Double timeDelta = eventTime - focalTime;
            return amountFormulaService.applyFormula(eventValue, null, interestRate, timeDelta).getPrincipal();
        } else {
            return eventValue; 
        }
    }

    private void validateParameters(
            List<FlowInputOutput> originalFlows,
            List<FlowInputOutput> proposedFlows,
            FlowInputOutput flowOfInterest,
            Double focalTime,
            Double interestRate) {

        if (Objects.isNull(originalFlows) || Objects.isNull(proposedFlows) || Objects.isNull(flowOfInterest)) {
            throw new IllegalExternalParameterException("Os esquemas de pagamento e o evento de interesse não podem ser nulos.");
        }

        if (Objects.isNull(focalTime) || focalTime <= 0) {
            throw new IllegalExternalParameterException("O tempo focal deve ser maior que zero e não pode ser nulo.");
        }

        if (Objects.isNull(interestRate) || interestRate <= 0) {
            throw new IllegalExternalParameterException("A taxa de juros deve ser maior que zero e não pode ser nula.");
        }

        originalFlows.forEach(event -> this.validatesEvent(event, "evento do esquema original"));
        proposedFlows.forEach(event -> this.validatesEvent(event, "evento do esquema proposto"));

        this.validatesEvent(flowOfInterest, "evento de interesse");
    }

    private void validatesEvent(FlowInputOutput event, String eventDescription) {
        if (Objects.isNull(event)) {
            throw new IllegalExternalParameterException("Um " + eventDescription + " é nulo.");
        }

        if (Objects.isNull(event.getValue()) || event.getValue() <= 0) {
            throw new IllegalExternalParameterException("O valor do " + eventDescription + " deve ser maior que zero e não pode ser nulo.");
        }

        if (Objects.isNull(event.getTime()) || event.getTime() <= 0) {
            throw new IllegalExternalParameterException("O tempo do " + eventDescription + " deve ser maior que zero e não pode ser nulo.");
        }
    }
}
