package dev.liaskarllate.finmathly.calculation.interest.shared.service;

import java.math.BigDecimal;

import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

/**
 * Base service class for calculating equivalent interest rates.
 */
public abstract class BaseEquivalentInterestRateService {
    public BigDecimal calculateEquivalentInterestRate(
            BigDecimal interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to) {

        this.validateProvidedParameters(interestRate, from, to);

        return this.calculate(interestRate, from, to);
    }

    protected void validateProvidedParameters(
            BigDecimal interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to) {

        if (interestRate == null) {
            throw new InvalidInputException("Interest rate is required. Please provide a value, such as 0.05 for 5%.");
        }

        if (interestRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInputException(
                    "Interest rate cannot be negative. Please provide a positive value, such as 0.05 for 5%.");
        }

        if (from == null) {
            throw new InvalidInputException(
                    "The original capitalization period is required. Please provide a period, such as 'MONTHLY' or 'YEARLY'.");
        }

        if (to == null) {
            throw new InvalidInputException(
                    "The target capitalization period is required. Please provide a period, such as 'MONTHLY' or 'YEARLY'.");
        }
    }

    protected abstract BigDecimal calculate(
            BigDecimal interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to);
}