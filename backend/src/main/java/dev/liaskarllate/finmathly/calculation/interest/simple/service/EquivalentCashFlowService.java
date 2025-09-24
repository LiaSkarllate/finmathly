package dev.liaskarllate.finmathly.calculation.interest.simple.service;

import java.util.List;
import java.util.Objects;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.calculation.interest.shared.model.FlowInput;
import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;
import lombok.AllArgsConstructor;

/**
 * Service class for calculating the missing value of a target flow of a
 * proposed cash flow to ensure equivalence with the original cash flow.
 */
@AllArgsConstructor
@Service
public class EquivalentCashFlowService {
    private final AmountFormulaService amountFormulaService;

    public BigDecimal calculateRequiredTargetFlowValue(
            List<FlowInput> originalFlows,
            List<FlowInput> proposedFlows,
            FlowInput targetFlow,
            BigDecimal focalTime,
            BigDecimal interestRate) {

        this.validateProvidedParameters(
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

    private BigDecimal calculate(
            List<FlowInput> originalFlows,
            List<FlowInput> proposedFlows,
            FlowInput targetFlow,
            BigDecimal focalTime,
            BigDecimal interestRate) {

        BigDecimal adjustedOriginalFlowsValuesSum = this.calculateAdjustedFlowsValuesSum(originalFlows, interestRate,
                focalTime);
        BigDecimal adjustedProposedFlowsValuesSum = this.calculateAdjustedFlowsValuesSum(proposedFlows, interestRate,
                focalTime);

        BigDecimal netDifference = adjustedOriginalFlowsValuesSum.subtract(adjustedProposedFlowsValuesSum);
        BigDecimal flowTime = targetFlow.getTime();

        BigDecimal targetValue = netDifference;

        if (flowTime.compareTo(focalTime) < 0) {
            targetValue = amountFormulaService.applyFormula(netDifference, interestRate, focalTime.subtract(flowTime))
                    .getAmount();
        }

        if (flowTime.compareTo(focalTime) > 0) {
            targetValue = amountFormulaService
                    .applyFormula(netDifference, null, interestRate, flowTime.subtract(focalTime)).getPrincipal();
        }

        return targetValue;
    }

    private BigDecimal calculateAdjustedFlowsValuesSum(
            List<FlowInput> flows,
            BigDecimal interestRate,
            BigDecimal focalTime) {
        return flows.stream()
                .map(flow -> this.calculateAdjustedFlowValue(flow, interestRate, focalTime))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateAdjustedFlowValue(
            FlowInput flow,
            BigDecimal interestRate,
            BigDecimal focalTime) {

        BigDecimal flowTime = flow.getTime();
        BigDecimal flowValue = flow.getValue();

        if (flowTime.compareTo(focalTime) < 0) {
            BigDecimal timeDelta = focalTime.subtract(flowTime);
            return amountFormulaService.applyFormula(flowValue, interestRate, timeDelta).getAmount();
        } else if (flowTime.compareTo(focalTime) > 0) {
            BigDecimal timeDelta = flowTime.subtract(focalTime);
            return amountFormulaService.applyFormula(flowValue, null, interestRate, timeDelta).getPrincipal();
        } else {
            return flowValue;
        }
    }

    private void validateProvidedParameters(
            List<FlowInput> originalFlows,
            List<FlowInput> proposedFlows,
            FlowInput targetFlow,
            BigDecimal focalTime,
            BigDecimal interestRate) {

        if (Objects.isNull(originalFlows) || Objects.isNull(proposedFlows) || Objects.isNull(targetFlow)) {
            throw new InvalidInputException("Payment flows and the target flow cannot be null.");
        }

        if (Objects.isNull(focalTime) || focalTime.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("The focal time must be bigger than zero and cannot be null.");
        }

        if (Objects.isNull(interestRate) || interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("The interest rate must be bigger than zero and cannot be null.");
        }

        originalFlows.forEach(flow -> this.validateFlow(flow, "flow from the original ones"));
        proposedFlows.forEach(flow -> this.validateFlow(flow, "flow from the proposed ones"));

        this.validateFlow(targetFlow, "target flow");
    }

    private void validateFlow(FlowInput flow, String flowDescription) {
        if (Objects.isNull(flow)) {
            throw new InvalidInputException("One " + flowDescription + " is null.");
        }

        if (Objects.isNull(flow.getValue()) || flow.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException(
                    "The value of " + flowDescription + " must be bigger than zero and it can't be null.");
        }

        if (Objects.isNull(flow.getTime()) || flow.getTime().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException(
                    "The time of " + flowDescription + " must be bigger than zero and it can't be null.");
        }
    }
}