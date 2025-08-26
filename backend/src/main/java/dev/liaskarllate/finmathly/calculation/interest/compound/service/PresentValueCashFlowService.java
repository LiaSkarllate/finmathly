package dev.liaskarllate.finmathly.calculation.interest.compound.service;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.calculation.interest.shared.model.FlowInput;
import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;
import lombok.AllArgsConstructor;

/**
 * Service class for calculating the present value of a cash flow.
 */
@AllArgsConstructor
@Service
public class PresentValueCashFlowService {
    private final FutureValueFormulaService futureValueFormulaService;

    public BigDecimal calculatePresentValueOfACashFlow(
            List<FlowInput> flows,
            BigDecimal interestRate) {

        this.validateProvidedParameters(
                flows,
                interestRate);

        return this.calculate(
                flows,
                interestRate);
    }

    private void validateProvidedParameters(
            List<FlowInput> flows,
            BigDecimal interestRate) {

        if (flows == null || flows.isEmpty()) {
            throw new InvalidInputException("Flows are required. Please provide a cash flow.");
        }

        if (interestRate == null) {
            throw new InvalidInputException("Interest rate is required. Please provide a value, such as 0.05 for 5%.");
        }

        if (interestRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInputException(
                    "Interest rate cannot be negative. Please provide a positive value, such as 0.05 for 5%.");
        }

        for (FlowInput flow : flows) {
            if (flow.getValue() == null) {
                throw new InvalidInputException(
                        "The value of at least one flow has not been provided. Please provide all flow values.");
            }

            if (flow.getTime() == null) {
                throw new InvalidInputException(
                        "The time of at least one flow has not been provided. Please provide all flow times.");
            }
        }
    }

    private BigDecimal calculate(
            List<FlowInput> flows,
            BigDecimal interestRate) {

        return flows.stream()
                .map(flow -> this.futureValueFormulaService
                        .applyFormula(flow.getValue(), null, interestRate, flow.getTime())
                        .getPresentValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}