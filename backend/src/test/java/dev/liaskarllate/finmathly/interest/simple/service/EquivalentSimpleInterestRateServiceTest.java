package dev.liaskarllate.finmathly.interest.simple.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.calculation.interest.simple.service.EquivalentSimpleInterestRateService;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

class EquivalentSimpleInterestRateServiceTest {
    private EquivalentSimpleInterestRateService equivalentSimpleInterestRateService;

    @BeforeEach
    void setUp() {
        equivalentSimpleInterestRateService = new EquivalentSimpleInterestRateService();
    }

    @Test
    void shouldConvertMonthlyRateToYearlyRate() {
        BigDecimal interestRate = new BigDecimal("0.09");

        BigDecimal result = equivalentSimpleInterestRateService.calculateEquivalentInterestRate(
                interestRate, CapitalizationPeriod.QUARTERLY, CapitalizationPeriod.YEARLY);

        assertThat(result).isEqualByComparingTo(new BigDecimal("0.3600"));
        assertThat(result.scale()).isEqualTo(4);
    }

    @Test
    void shouldConvertYearlyRateToMonthlyRate() {
        BigDecimal interestRate = new BigDecimal("0.36");

        BigDecimal result = equivalentSimpleInterestRateService.calculateEquivalentInterestRate(
                interestRate, CapitalizationPeriod.YEARLY, CapitalizationPeriod.QUARTERLY);

        assertThat(result).isEqualByComparingTo(new BigDecimal("0.0900"));
        assertThat(result.scale()).isEqualTo(4);
    }

    @Test
    void shouldThrowWhenInterestRateIsNull() {
        assertThatThrownBy(() -> equivalentSimpleInterestRateService.calculateEquivalentInterestRate(
                null, CapitalizationPeriod.MONTHLY, CapitalizationPeriod.YEARLY))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenInterestRateIsNegative() {
        assertThatThrownBy(() -> equivalentSimpleInterestRateService.calculateEquivalentInterestRate(
                new BigDecimal("-0.01"), CapitalizationPeriod.MONTHLY, CapitalizationPeriod.YEARLY))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenFromPeriodIsNull() {
        assertThatThrownBy(() -> equivalentSimpleInterestRateService.calculateEquivalentInterestRate(
                new BigDecimal("0.09"), null, CapitalizationPeriod.YEARLY))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void shouldThrowWhenToPeriodIsNull() {
        assertThatThrownBy(() -> equivalentSimpleInterestRateService.calculateEquivalentInterestRate(
                new BigDecimal("0.09"), CapitalizationPeriod.MONTHLY, null))
                .isInstanceOf(InvalidInputException.class);
    }
}
