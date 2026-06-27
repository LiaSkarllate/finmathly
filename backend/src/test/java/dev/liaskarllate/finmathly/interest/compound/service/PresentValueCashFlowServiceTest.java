package dev.liaskarllate.finmathly.interest.compound.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.calculation.interest.compound.service.FutureValueFormulaService;
import dev.liaskarllate.finmathly.calculation.interest.compound.service.PresentValueCashFlowService;
import dev.liaskarllate.finmathly.calculation.interest.shared.model.FlowInput;
import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

class PresentValueCashFlowServiceTest {
    private PresentValueCashFlowService presentValueCashFlowService;

    @BeforeEach
    void setUp() {
        presentValueCashFlowService = new PresentValueCashFlowService(new FutureValueFormulaService());
    }

    @Test
    void shouldCalculatePresentValueOfMultipleFlows() {
        List<FlowInput> flows = List.of(
                new FlowInput(new BigDecimal("15000"), new BigDecimal("2")),
                new FlowInput(new BigDecimal("40000"), new BigDecimal("5")),
                new FlowInput(new BigDecimal("50000"), new BigDecimal("6")),
                new FlowInput(new BigDecimal("70000"), new BigDecimal("8")));

        BigDecimal result = presentValueCashFlowService.calculatePresentValueOfACashFlow(flows, new BigDecimal("0.03"));

        assertThat(result).isEqualByComparingTo(new BigDecimal("145776.15"));
        assertThat(result.scale()).isEqualTo(2);
    }

    @Test
    void shouldCalculatePresentValueOfASingleFlow() {
        List<FlowInput> flows = List.of(new FlowInput(new BigDecimal("15801.71"), new BigDecimal("8")));

        BigDecimal result = presentValueCashFlowService.calculatePresentValueOfACashFlow(flows, new BigDecimal("0.035"));

        assertThat(result).isEqualByComparingTo(new BigDecimal("12000.00"));
        assertThat(result.scale()).isEqualTo(2);
    }

    @Test
    void shouldThrowWhenFlowsIsEmpty() {
        assertThatThrownBy(
                () -> presentValueCashFlowService.calculatePresentValueOfACashFlow(List.of(), new BigDecimal("0.03")))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenInterestRateIsNull() {
        List<FlowInput> flows = List.of(new FlowInput(new BigDecimal("15000"), new BigDecimal("2")));

        assertThatThrownBy(() -> presentValueCashFlowService.calculatePresentValueOfACashFlow(flows, null))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenInterestRateIsNegative() {
        List<FlowInput> flows = List.of(new FlowInput(new BigDecimal("15000"), new BigDecimal("2")));

        assertThatThrownBy(
                () -> presentValueCashFlowService.calculatePresentValueOfACashFlow(flows, new BigDecimal("-0.01")))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenAFlowValueIsMissing() {
        List<FlowInput> flows = List.of(new FlowInput(null, new BigDecimal("2")));

        assertThatThrownBy(
                () -> presentValueCashFlowService.calculatePresentValueOfACashFlow(flows, new BigDecimal("0.03")))
                .isInstanceOf(InvalidInputException.class);
    }
}
