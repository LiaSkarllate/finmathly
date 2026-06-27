package dev.liaskarllate.finmathly.interest.compound.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.calculation.interest.compound.model.CompoundInterestFormulaOutput;
import dev.liaskarllate.finmathly.calculation.interest.compound.service.CompoundInterestFormulaService;
import dev.liaskarllate.finmathly.shared.exception.InvalidMissingParametersCountException;

class CompoundInterestFormulaServiceTest {
    private CompoundInterestFormulaService compoundInterestFormulaService;

    @BeforeEach
    void setUp() {
        compoundInterestFormulaService = new CompoundInterestFormulaService();
    }

    @Test
    void shouldCalculateInterestWhenInterestIsNull() {
        BigDecimal presentValue = new BigDecimal("10000.0");
        BigDecimal interestRate = new BigDecimal("0.05");
        BigDecimal time = new BigDecimal("3.0");

        CompoundInterestFormulaOutput output = compoundInterestFormulaService.applyFormula(null, presentValue,
                interestRate, time);

        assertThat(output.getInterest()).isEqualByComparingTo(new BigDecimal("1576.25"));
        assertThat(output.getInterest().scale()).isEqualTo(2);
        assertThat(output.getPresentValue()).isEqualByComparingTo(presentValue);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculatePresentValueWhenPresentValueIsNull() {
        BigDecimal interest = new BigDecimal("1576.25");
        BigDecimal interestRate = new BigDecimal("0.05");
        BigDecimal time = new BigDecimal("3.0");

        CompoundInterestFormulaOutput output = compoundInterestFormulaService.applyFormula(interest, null,
                interestRate, time);

        assertThat(output.getPresentValue()).isEqualByComparingTo(new BigDecimal("10000.00"));
        assertThat(output.getInterest()).isEqualByComparingTo(interest);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculateInterestRateWhenInterestRateIsNull() {
        BigDecimal interest = new BigDecimal("1576.25");
        BigDecimal presentValue = new BigDecimal("10000.0");
        BigDecimal time = new BigDecimal("3.0");

        CompoundInterestFormulaOutput output = compoundInterestFormulaService.applyFormula(interest, presentValue,
                null, time);

        assertThat(output.getInterestRate()).isEqualByComparingTo(new BigDecimal("0.0500"));
        assertThat(output.getInterestRate().scale()).isEqualTo(4);
        assertThat(output.getInterest()).isEqualByComparingTo(interest);
        assertThat(output.getPresentValue()).isEqualByComparingTo(presentValue);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculateTimeWhenTimeIsNull() {
        BigDecimal interest = new BigDecimal("1576.25");
        BigDecimal presentValue = new BigDecimal("10000.0");
        BigDecimal interestRate = new BigDecimal("0.05");

        CompoundInterestFormulaOutput output = compoundInterestFormulaService.applyFormula(interest, presentValue,
                interestRate, null);

        assertThat(output.getTime()).isEqualByComparingTo(new BigDecimal("3.00"));
        assertThat(output.getInterest()).isEqualByComparingTo(interest);
        assertThat(output.getPresentValue()).isEqualByComparingTo(presentValue);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
    }

    @Test
    void shouldThrowWhenNoParameterIsNull() {
        BigDecimal interest = new BigDecimal("1576.25");
        BigDecimal presentValue = new BigDecimal("10000.0");
        BigDecimal interestRate = new BigDecimal("0.05");
        BigDecimal time = new BigDecimal("3.0");

        assertThatThrownBy(() -> compoundInterestFormulaService.applyFormula(interest, presentValue, interestRate, time))
                .isInstanceOf(InvalidMissingParametersCountException.class);
    }

    @Test
    void shouldThrowWhenMoreThanOneParameterIsNull() {
        BigDecimal interestRate = new BigDecimal("0.05");
        BigDecimal time = new BigDecimal("3.0");

        assertThatThrownBy(() -> compoundInterestFormulaService.applyFormula(null, null, interestRate, time))
                .isInstanceOfSatisfying(InvalidMissingParametersCountException.class, e -> {
                    assertThat(e.getMissingParametersCount()).isEqualTo(2);
                    assertThat(e.getExpectedMissingParametersCount()).isEqualTo(1);
                    assertThat(e.getTotalParametersCount()).isEqualTo(4);
                });
    }
}
