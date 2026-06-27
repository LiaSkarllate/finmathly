package dev.liaskarllate.finmathly.interest.simple.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.calculation.interest.shared.model.FlowInput;
import dev.liaskarllate.finmathly.calculation.interest.simple.service.AmountFormulaService;
import dev.liaskarllate.finmathly.calculation.interest.simple.service.EquivalentCashFlowService;
import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

class EquivalentCashFlowServiceTest {
    private EquivalentCashFlowService equivalentCashFlowService;

    @BeforeEach
    void setUp() {
        equivalentCashFlowService = new EquivalentCashFlowService(new AmountFormulaService());
    }

    @Test
    void shouldCalculateRequiredTargetFlowValueWhenTargetIsBeforeFocalDate() {
        List<FlowInput> originalFlows = List.of(new FlowInput(new BigDecimal("10000"), new BigDecimal("1")));
        List<FlowInput> proposedFlows = List.of(new FlowInput(new BigDecimal("15000"), new BigDecimal("2")));
        FlowInput targetFlow = new FlowInput(BigDecimal.ZERO, BigDecimal.ZERO);

        BigDecimal result = equivalentCashFlowService.calculateRequiredTargetFlowValue(
                originalFlows, proposedFlows, targetFlow, new BigDecimal("2"), new BigDecimal("0.05"));

        assertThat(result).isEqualByComparingTo(new BigDecimal("-4090.91"));
        assertThat(result.scale()).isEqualTo(2);
    }

    @Test
    void shouldCalculateRequiredTargetFlowValueWhenTargetIsAfterFocalDate() {
        List<FlowInput> originalFlows = List.of(new FlowInput(new BigDecimal("10000"), new BigDecimal("1")));
        List<FlowInput> proposedFlows = List.of(new FlowInput(new BigDecimal("15000"), new BigDecimal("2")));
        FlowInput targetFlow = new FlowInput(BigDecimal.ZERO, new BigDecimal("5"));

        BigDecimal result = equivalentCashFlowService.calculateRequiredTargetFlowValue(
                originalFlows, proposedFlows, targetFlow, new BigDecimal("2"), new BigDecimal("0.05"));

        assertThat(result).isEqualByComparingTo(new BigDecimal("-5175.00"));
        assertThat(result.scale()).isEqualTo(2);
    }

    @Test
    void shouldAllowTargetFlowWithZeroValueAndZeroTime() {
        List<FlowInput> originalFlows = List.of(new FlowInput(new BigDecimal("10000"), new BigDecimal("1")));
        List<FlowInput> proposedFlows = List.of(new FlowInput(new BigDecimal("15000"), new BigDecimal("2")));
        FlowInput targetFlow = new FlowInput(BigDecimal.ZERO, BigDecimal.ZERO);

        BigDecimal result = equivalentCashFlowService.calculateRequiredTargetFlowValue(
                originalFlows, proposedFlows, targetFlow, new BigDecimal("2"), new BigDecimal("0.05"));

        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowWhenAFlowHasNegativeTime() {
        List<FlowInput> originalFlows = List.of(new FlowInput(new BigDecimal("10000"), new BigDecimal("-1")));
        List<FlowInput> proposedFlows = List.of(new FlowInput(new BigDecimal("15000"), new BigDecimal("2")));
        FlowInput targetFlow = new FlowInput(BigDecimal.ZERO, BigDecimal.ZERO);

        assertThatThrownBy(() -> equivalentCashFlowService.calculateRequiredTargetFlowValue(
                originalFlows, proposedFlows, targetFlow, new BigDecimal("2"), new BigDecimal("0.05")))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenAnOriginalFlowHasZeroValue() {
        List<FlowInput> originalFlows = List.of(new FlowInput(BigDecimal.ZERO, new BigDecimal("1")));
        List<FlowInput> proposedFlows = List.of(new FlowInput(new BigDecimal("15000"), new BigDecimal("2")));
        FlowInput targetFlow = new FlowInput(BigDecimal.ZERO, BigDecimal.ZERO);

        assertThatThrownBy(() -> equivalentCashFlowService.calculateRequiredTargetFlowValue(
                originalFlows, proposedFlows, targetFlow, new BigDecimal("2"), new BigDecimal("0.05")))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenFocalTimeIsNull() {
        List<FlowInput> originalFlows = List.of(new FlowInput(new BigDecimal("10000"), new BigDecimal("1")));
        List<FlowInput> proposedFlows = List.of(new FlowInput(new BigDecimal("15000"), new BigDecimal("2")));
        FlowInput targetFlow = new FlowInput(BigDecimal.ZERO, BigDecimal.ZERO);

        assertThatThrownBy(() -> equivalentCashFlowService.calculateRequiredTargetFlowValue(
                originalFlows, proposedFlows, targetFlow, null, new BigDecimal("0.05")))
                .isInstanceOf(InvalidInputException.class);
    }
}
