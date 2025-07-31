package dev.liaskarllate.finmathly.interest.simple.service;

import java.math.BigDecimal;
import dev.liaskarllate.finmathly.interest.simple.model.SimpleInterestFormulaOutput;
import dev.liaskarllate.finmathly.shared.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.shared.exception.TooManyMissingArgumentsException;

import org.springframework.stereotype.Service;

/**
 * Service class for applying the simple interest formula.
 */
@Service
public class SimpleInterestFormulaService {
    public SimpleInterestFormulaOutput applyFormula(
            BigDecimal interest,
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) {

        this.validateTheNumberOfProvidedParameters(
                interest,
                principal,
                interestRate,
                time);

        return this.calculate(
                interest,
                principal,
                interestRate,
                time);
    }

    private void validateTheNumberOfProvidedParameters(
            BigDecimal interest,
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) {
        int missingParametersCount = 0;

        if (interest == null)
            missingParametersCount++;
        if (principal == null)
            missingParametersCount++;
        if (interestRate == null)
            missingParametersCount++;
        if (time == null)
            missingParametersCount++;

        if (missingParametersCount != 1) {
            throw new TooManyMissingArgumentsException(missingParametersCount, 1, 4);
        }
    }

    private SimpleInterestFormulaOutput calculate(
            BigDecimal interest,
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) {

        SimpleInterestFormulaOutput simpleInterestFormulaOutput = new SimpleInterestFormulaOutput(
                interest,
                principal,
                interestRate,
                time);

        if (interest == null) {
            interest = principal
                    .multiply(interestRate)
                    .multiply(time);
            simpleInterestFormulaOutput.setInterest(interest);
        } else if (principal == null) {
            BigDecimal denominator = interestRate.multiply(time);
            principal = interest.divide(denominator);
            simpleInterestFormulaOutput.setPrincipal(principal);
        } else if (interestRate == null) {
            BigDecimal denominator = principal.multiply(time);
            interestRate = interest.divide(denominator);
            simpleInterestFormulaOutput.setInterestRate(interestRate);
        } else if (time == null) {
            BigDecimal denominator = principal.multiply(interestRate);
            time = interest.divide(denominator);
            simpleInterestFormulaOutput.setTime(time);
        } else {
            throw new NothingToBeCalculatedException();
        }

        return simpleInterestFormulaOutput;
    }
}