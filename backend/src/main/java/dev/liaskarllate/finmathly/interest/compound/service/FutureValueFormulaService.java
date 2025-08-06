package dev.liaskarllate.finmathly.interest.compound.service;

import java.math.BigDecimal;
import java.math.MathContext;

import dev.liaskarllate.finmathly.shared.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.shared.exception.TooManyMissingArgumentsException;
import dev.liaskarllate.finmathly.interest.compound.model.FutureValueFormulaOutput;

import org.springframework.stereotype.Service;

import ch.obermuhlner.math.big.BigDecimalMath;

/**
 * Service class for applying the compound interest future value formula.
 */
@Service
public class FutureValueFormulaService {
    public FutureValueFormulaOutput applyFormula(
            BigDecimal presentValue,
            BigDecimal interestRate,
            BigDecimal time) {

        return this.applyFormula(
                null,
                presentValue,
                interestRate,
                time);
    }

    public FutureValueFormulaOutput applyFormula(
            BigDecimal futureValue,
            BigDecimal presentValue,
            BigDecimal interestRate,
            BigDecimal time) {

        this.validateTheNumberOfProvidedParameters(
                futureValue,
                presentValue,
                interestRate,
                time);

        return this.calculate(
                futureValue,
                presentValue,
                interestRate,
                time);
    }

    private void validateTheNumberOfProvidedParameters(
            BigDecimal futureValue,
            BigDecimal presentValue,
            BigDecimal interestRate,
            BigDecimal time) {
        int missingParametersCount = 0;

        if (futureValue == null)
            missingParametersCount++;
        if (presentValue == null)
            missingParametersCount++;
        if (interestRate == null)
            missingParametersCount++;
        if (time == null)
            missingParametersCount++;

        if (missingParametersCount != 1) {
            throw new TooManyMissingArgumentsException(missingParametersCount, 1, 4);
        }
    }

    private FutureValueFormulaOutput calculate(
            BigDecimal futureValue,
            BigDecimal presentValue,
            BigDecimal interestRate,
            BigDecimal time) {

        FutureValueFormulaOutput futureValueFormulaOutput = new FutureValueFormulaOutput(
                futureValue,
                presentValue,
                interestRate,
                time);

        if (futureValue == null) {
            BigDecimal base = BigDecimal.ONE.add(interestRate);
            BigDecimal factor = BigDecimalMath.pow(base, time, MathContext.DECIMAL64);
            futureValue = presentValue.multiply(factor, MathContext.DECIMAL64);
            futureValueFormulaOutput.setFutureValue(futureValue);

        } else if (presentValue == null) {
            BigDecimal base = BigDecimal.ONE.add(interestRate);
            BigDecimal factor = BigDecimalMath.pow(base, time, MathContext.DECIMAL64);
            presentValue = futureValue.divide(factor, MathContext.DECIMAL64);
            futureValueFormulaOutput.setPresentValue(presentValue);

        } else if (interestRate == null) {
            BigDecimal ratio = futureValue.divide(presentValue, MathContext.DECIMAL64);
            BigDecimal root = BigDecimalMath.pow(ratio, BigDecimal.ONE.divide(time, MathContext.DECIMAL64),
                    MathContext.DECIMAL64);
            interestRate = root.subtract(BigDecimal.ONE, MathContext.DECIMAL64);
            futureValueFormulaOutput.setInterestRate(interestRate);

        } else if (time == null) {
            BigDecimal ratio = futureValue.divide(presentValue, MathContext.DECIMAL64);
            BigDecimal numerator = BigDecimalMath.log(ratio, MathContext.DECIMAL64);
            BigDecimal denominator = BigDecimalMath.log(BigDecimal.ONE.add(interestRate), MathContext.DECIMAL64);
            time = numerator.divide(denominator, MathContext.DECIMAL64);
            futureValueFormulaOutput.setTime(time);

        } else {
            throw new NothingToBeCalculatedException();
        }

        return futureValueFormulaOutput;
    }
}