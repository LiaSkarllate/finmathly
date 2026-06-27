package dev.liaskarllate.finmathly.interest.compound.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.calculation.interest.compound.service.EquivalentCompoundInterestRateService;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

class EquivalentCompoundInterestRateServiceTest {
    private EquivalentCompoundInterestRateService equivalentCompoundInterestRateService;

    @BeforeEach
    void setUp() {
        equivalentCompoundInterestRateService = new EquivalentCompoundInterestRateService();
    }

    @Test
    void shouldConvertYearlyRateToMonthlyRate() {
        BigDecimal interestRate = new BigDecimal("0.25");

        BigDecimal result = equivalentCompoundInterestRateService.calculateEquivalentInterestRate(
                interestRate, CapitalizationPeriod.YEARLY, CapitalizationPeriod.MONTHLY);

        assertThat(result).isEqualByComparingTo(new BigDecimal("0.0188"));
        assertThat(result.scale()).isEqualTo(4);
    }

    @Test
    void shouldReturnSameRateWhenPeriodsAreEqual() {
        BigDecimal interestRate = new BigDecimal("0.05");

        BigDecimal result = equivalentCompoundInterestRateService.calculateEquivalentInterestRate(
                interestRate, CapitalizationPeriod.MONTHLY, CapitalizationPeriod.MONTHLY);

        assertThat(result).isEqualByComparingTo(new BigDecimal("0.0500"));
        assertThat(result.scale()).isEqualTo(4);
    }

    @Test
    void shouldThrowWhenInterestRateIsNull() {
        assertThatThrownBy(() -> equivalentCompoundInterestRateService.calculateEquivalentInterestRate(
                null, CapitalizationPeriod.MONTHLY, CapitalizationPeriod.YEARLY))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenInterestRateIsNegative() {
        assertThatThrownBy(() -> equivalentCompoundInterestRateService.calculateEquivalentInterestRate(
                new BigDecimal("-0.01"), CapitalizationPeriod.MONTHLY, CapitalizationPeriod.YEARLY))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenFromPeriodIsNull() {
        assertThatThrownBy(() -> equivalentCompoundInterestRateService.calculateEquivalentInterestRate(
                new BigDecimal("0.25"), null, CapitalizationPeriod.MONTHLY))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenToPeriodIsNull() {
        assertThatThrownBy(() -> equivalentCompoundInterestRateService.calculateEquivalentInterestRate(
                new BigDecimal("0.25"), CapitalizationPeriod.YEARLY, null))
                .isInstanceOf(InvalidInputException.class);
    }
}
