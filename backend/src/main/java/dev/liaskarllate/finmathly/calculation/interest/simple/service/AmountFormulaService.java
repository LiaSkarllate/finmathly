package dev.liaskarllate.finmathly.calculation.interest.simple.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

import dev.liaskarllate.finmathly.shared.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.calculation.interest.simple.model.AmountFormulaOutput;
import dev.liaskarllate.finmathly.shared.exception.InvalidMissingParametersCountException;

/**
 * Service class for applying the simple interest amount formula.
 */
@Service
public class AmountFormulaService {
    public AmountFormulaOutput applyFormula(
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) {

        return this.applyFormula(
                null,
                principal,
                interestRate,
                time);
    }

    public AmountFormulaOutput applyFormula(
            BigDecimal amount,
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) {

        this.validateTheNumberOfParameters(
                amount,
                principal,
                interestRate,
                time);

        return this.calculate(
                amount,
                principal,
                interestRate,
                time);
    }

    private void validateTheNumberOfParameters(
            BigDecimal amount,
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) {
        int missingParametersCount = 0;

        if (amount == null)
            missingParametersCount++;
        if (principal == null)
            missingParametersCount++;
        if (interestRate == null)
            missingParametersCount++;
        if (time == null)
            missingParametersCount++;

        if (missingParametersCount != 1) {
            throw new InvalidMissingParametersCountException(missingParametersCount, 1, 4);
        }
    }

    private AmountFormulaOutput calculate(
            BigDecimal amount,
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) {

        AmountFormulaOutput amountFormulaOutput = new AmountFormulaOutput(
                amount,
                principal,
                interestRate,
                time);

        if (amount == null) {
            BigDecimal fcs = BigDecimal.ONE.add(interestRate.multiply(time));
            amount = principal.multiply(fcs);
            amountFormulaOutput.setAmount(amount);

        } else if (principal == null) {
            BigDecimal fcs = BigDecimal.ONE.add(interestRate.multiply(time));
            BigDecimal fas = BigDecimal.ONE.divide(fcs, MathContext.DECIMAL64);
            principal = amount.multiply(fas);
            amountFormulaOutput.setPrincipal(principal);

        } else if (interestRate == null) {
            BigDecimal ratio = amount.divide(principal, MathContext.DECIMAL64);
            interestRate = ratio.subtract(BigDecimal.ONE).divide(time, MathContext.DECIMAL64);
            amountFormulaOutput.setInterestRate(interestRate);

        } else if (time == null) {
            BigDecimal ratio = amount.divide(principal, MathContext.DECIMAL64);
            time = ratio.subtract(BigDecimal.ONE).divide(interestRate, MathContext.DECIMAL64);
            amountFormulaOutput.setTime(time);

        } else {
            throw new NothingToBeCalculatedException();
        }

        return amountFormulaOutput;
    }
}