package dev.liaskarllate.finmathly.calculation.interest.compound.service;

import java.math.BigDecimal;
import java.math.MathContext;

import dev.liaskarllate.finmathly.shared.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.calculation.interest.compound.model.CompoundInterestFormulaOutput;
import dev.liaskarllate.finmathly.shared.exception.InvalidMissingParametersCountException;
import dev.liaskarllate.finmathly.shared.util.Rounding;

import org.springframework.stereotype.Service;

import ch.obermuhlner.math.big.BigDecimalMath;

/**
 * Service class for applying the compound interest formula.
 */
@Service
public class CompoundInterestFormulaService {

    public CompoundInterestFormulaOutput applyFormula(
            BigDecimal interest,
            BigDecimal presentValue,
            BigDecimal interestRate,
            BigDecimal time) {

        this.validateTheNumberOfProvidedParameters(
                interest,
                presentValue,
                interestRate,
                time);

        return this.calculate(
                interest,
                presentValue,
                interestRate,
                time);
    }

    private void validateTheNumberOfProvidedParameters(
            BigDecimal interest,
            BigDecimal presentValue,
            BigDecimal interestRate,
            BigDecimal time) {
        int missingParametersCount = 0;

        if (interest == null)
            missingParametersCount++;
        if (presentValue == null)
            missingParametersCount++;
        if (interestRate == null)
            missingParametersCount++;
        if (time == null)
            missingParametersCount++;

        if (missingParametersCount != 1) {
            throw new InvalidMissingParametersCountException(missingParametersCount, 1, 4);
        }
    }

    private CompoundInterestFormulaOutput calculate(
            BigDecimal interest,
            BigDecimal presentValue,
            BigDecimal interestRate,
            BigDecimal time) {

        CompoundInterestFormulaOutput compoundInterestFormulaOutput = new CompoundInterestFormulaOutput(
                interest,
                presentValue,
                interestRate,
                time);

        if (interest == null) {
            BigDecimal base = BigDecimal.ONE.add(interestRate);
            BigDecimal exponentiated = BigDecimalMath.pow(base, time, MathContext.DECIMAL64);
            BigDecimal futureValue = presentValue.multiply(exponentiated, MathContext.DECIMAL64);
            interest = futureValue.subtract(presentValue, MathContext.DECIMAL64);
            compoundInterestFormulaOutput.setInterest(Rounding.money(interest));
        } else if (presentValue == null) {
            BigDecimal base = BigDecimal.ONE.add(interestRate);
            BigDecimal exponentiated = BigDecimalMath.pow(base, time, MathContext.DECIMAL64);
            BigDecimal denominator = exponentiated.subtract(BigDecimal.ONE, MathContext.DECIMAL64);
            presentValue = interest.divide(denominator, MathContext.DECIMAL64);
            compoundInterestFormulaOutput.setPresentValue(Rounding.money(presentValue));
        } else if (interestRate == null) {
            BigDecimal base = interest.add(presentValue, MathContext.DECIMAL64).divide(presentValue,
                    MathContext.DECIMAL64);
            BigDecimal root = BigDecimalMath.pow(base, BigDecimal.ONE.divide(time, MathContext.DECIMAL64),
                    MathContext.DECIMAL64);
            interestRate = root.subtract(BigDecimal.ONE, MathContext.DECIMAL64);
            compoundInterestFormulaOutput.setInterestRate(Rounding.rate(interestRate));
        } else if (time == null) {
            BigDecimal ratio = interest.add(presentValue, MathContext.DECIMAL64).divide(presentValue,
                    MathContext.DECIMAL64);
            BigDecimal numerator = BigDecimalMath.log(ratio, MathContext.DECIMAL64);
            BigDecimal denominator = BigDecimalMath.log(BigDecimal.ONE.add(interestRate), MathContext.DECIMAL64);
            time = numerator.divide(denominator, MathContext.DECIMAL64);
            compoundInterestFormulaOutput.setTime(Rounding.time(time));
        } else {
            throw new NothingToBeCalculatedException();
        }

        return compoundInterestFormulaOutput;
    }
}