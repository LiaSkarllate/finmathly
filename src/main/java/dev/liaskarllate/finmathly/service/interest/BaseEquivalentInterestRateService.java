package dev.liaskarllate.finmathly.service.interest;

import dev.liaskarllate.finmathly.exception.IllegalExternalParameterException;
import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;

/**
 * Base service class for calculating equivalent interest rates.
 */
public abstract class BaseEquivalentInterestRateService {

    /**
     * Calculates the equivalent interest rate from the original to the target capitalization period.
     *
     * @param interestRate	the original interest rate (as a decimal, e.g., 0.05 for 5%) for the original capitalization period
     * @param from 			the original capitalization period
     * @param to 			the target capitalization period
     * @return the equivalent interest rate for the target capitalization period
     */
    public Double calculateEquivalentInterestRate(
            Double interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to) {

        this.validateParametersProvided(interestRate, from, to);

        return this.calculate(interestRate, from, to);
    }

    protected void validateParametersProvided(
            Double interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to) {

        if (interestRate == null) {
            throw new IllegalExternalParameterException("Interest rate is required. Please provide a value, such as 0.05 for 5%.");
        }

        if (interestRate < 0) {
            throw new IllegalExternalParameterException("Interest rate cannot be negative. Please provide a positive value, such as 0.05 for 5%.");
        }

        if (from == null) {
            throw new IllegalExternalParameterException("The original capitalization period is required. Please provide a period, such as 'MONTHLY' or 'YEARLY'.");
        }

        if (to == null) {
            throw new IllegalExternalParameterException("The target capitalization period is required. Please provide a period, such as 'MONTHLY' or 'YEARLY'.");
        }
    }

    /**
     * Performs the calculation of the equivalent interest rate.
     */
    protected abstract Double calculate(
            Double interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to);
}