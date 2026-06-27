package dev.liaskarllate.finmathly.interest.compound.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.calculation.interest.compound.model.FutureValueFormulaOutput;
import dev.liaskarllate.finmathly.calculation.interest.compound.service.FutureValueFormulaService;
import dev.liaskarllate.finmathly.shared.exception.InvalidMissingParametersCountException;

class FutureValueFormulaServiceTest {
    private FutureValueFormulaService futureValueFormulaService;

    @BeforeEach
    void setUp() {
        futureValueFormulaService = new FutureValueFormulaService();
    }

    @Test
    void shouldCalculateFutureValueWhenFutureValueIsNull() {
        BigDecimal presentValue = new BigDecimal("12000.0");
        BigDecimal interestRate = new BigDecimal("0.035");
        BigDecimal time = new BigDecimal("8.0");

        FutureValueFormulaOutput output = futureValueFormulaService.applyFormula(presentValue, interestRate, time);

        assertThat(output.getFutureValue()).isEqualByComparingTo(new BigDecimal("15801.71"));
        assertThat(output.getFutureValue().scale()).isEqualTo(2);
        assertThat(output.getPresentValue()).isEqualByComparingTo(presentValue);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculatePresentValueWhenPresentValueIsNull() {
        BigDecimal futureValue = new BigDecimal("15801.71");
        BigDecimal interestRate = new BigDecimal("0.035");
        BigDecimal time = new BigDecimal("8.0");

        FutureValueFormulaOutput output = futureValueFormulaService.applyFormula(futureValue, null, interestRate,
                time);

        assertThat(output.getPresentValue()).isEqualByComparingTo(new BigDecimal("12000.00"));
        assertThat(output.getFutureValue()).isEqualByComparingTo(futureValue);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculateInterestRateWhenInterestRateIsNull() {
        BigDecimal futureValue = new BigDecimal("15801.71");
        BigDecimal presentValue = new BigDecimal("12000.0");
        BigDecimal time = new BigDecimal("8.0");

        FutureValueFormulaOutput output = futureValueFormulaService.applyFormula(futureValue, presentValue, null,
                time);

        assertThat(output.getInterestRate()).isEqualByComparingTo(new BigDecimal("0.0350"));
        assertThat(output.getInterestRate().scale()).isEqualTo(4);
        assertThat(output.getFutureValue()).isEqualByComparingTo(futureValue);
        assertThat(output.getPresentValue()).isEqualByComparingTo(presentValue);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculateTimeWhenTimeIsNull() {
        BigDecimal futureValue = new BigDecimal("15801.71");
        BigDecimal presentValue = new BigDecimal("12000.0");
        BigDecimal interestRate = new BigDecimal("0.035");

        FutureValueFormulaOutput output = futureValueFormulaService.applyFormula(futureValue, presentValue,
                interestRate, null);

        assertThat(output.getTime()).isEqualByComparingTo(new BigDecimal("8.00"));
        assertThat(output.getFutureValue()).isEqualByComparingTo(futureValue);
        assertThat(output.getPresentValue()).isEqualByComparingTo(presentValue);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
    }

    @Test
    void shouldThrowWhenNoParameterIsNull() {
        BigDecimal futureValue = new BigDecimal("15801.71");
        BigDecimal presentValue = new BigDecimal("12000.0");
        BigDecimal interestRate = new BigDecimal("0.035");
        BigDecimal time = new BigDecimal("8.0");

        assertThatThrownBy(
                () -> futureValueFormulaService.applyFormula(futureValue, presentValue, interestRate, time))
                .isInstanceOfSatisfying(InvalidMissingParametersCountException.class, e -> {
                    assertThat(e.getMissingParametersCount()).isEqualTo(0);
                    assertThat(e.getExpectedMissingParametersCount()).isEqualTo(1);
                    assertThat(e.getTotalParametersCount()).isEqualTo(4);
                });
    }

    @Test
    void shouldThrowWhenMoreThanOneParameterIsNull() {
        BigDecimal interestRate = new BigDecimal("0.035");
        BigDecimal time = new BigDecimal("8.0");

        assertThatThrownBy(() -> futureValueFormulaService.applyFormula(null, null, interestRate, time))
                .isInstanceOfSatisfying(InvalidMissingParametersCountException.class, e -> {
                    assertThat(e.getMissingParametersCount()).isEqualTo(2);
                    assertThat(e.getExpectedMissingParametersCount()).isEqualTo(1);
                    assertThat(e.getTotalParametersCount()).isEqualTo(4);
                });
    }
}
