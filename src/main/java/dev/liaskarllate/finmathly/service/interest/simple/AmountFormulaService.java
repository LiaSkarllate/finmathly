package dev.liaskarllate.finmathly.service.interest.simple;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.model.interest.simple.ObjectFactoryModel;
import dev.liaskarllate.finmathly.model.interest.simple.AmountFormulaOutput;
import dev.liaskarllate.finmathly.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.exception.TooManyMissingParametersException;

/**
 * Service class for applying the simple interest amount formula.
 */
@Service
public class AmountFormulaService {
	/**
     * Applies the simple interest amount formula to calculate the amount, the missing parameter in this case.
     *
     * @param principal     the principal (cannot be null).
     * @param interestRate  the interest rate (as a decimal, e.g., 0.05 for 5%) (cannot be null).
     * @param time          the time (cannot be null).
     * @return An {@link AmountFormulaOutput} object containing all values, including the calculated one.
     */
	public AmountFormulaOutput applyFormula(
			Double principal, 
			Double interestRate, 
			Double time) {
		
		return this.applyFormula(
				null, 
				principal, 
				interestRate, 
				time);
	}
	
	 /**
     * Applies the simple interest amount formula to calculate the missing parameter.
     *
     * @param amount        the amount (can be null if it needs to be calculated).
     * @param principal     the principal amount (can be null if it needs to be calculated).
     * @param interestRate  the interest rate (as a decimal, e.g., 0.05 for 5%) (can be null if it needs to be calculated).
     * @param time          the time (can be null if it needs to be calculated).
     * @return An {@link AmountFormulaOutput} object containing all values, including the calculated one.
     */
	public AmountFormulaOutput applyFormula(
			Double amount,
			Double principal, 
			Double interestRate, 
			Double time) {
		
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
			Double amount,
			Double principal,
			Double interestRate, 
			Double time) {
		int missingParametersCount = 0;

        if (amount == null) missingParametersCount++;
        if (principal == null) missingParametersCount++;
        if (interestRate == null) missingParametersCount++;
        if (time == null) missingParametersCount++;

		if (missingParametersCount != 1) {
			throw new TooManyMissingParametersException(missingParametersCount, 1, 4);
		}
	}

	/**
     * Performs the calculation of the missing parameter using the simple interest amount formula.
     */
	private AmountFormulaOutput calculate(
			Double amount, 
			Double principal, 
			Double interestRate, 
			Double time) {
		
		AmountFormulaOutput amountFormulaOutput = ObjectFactoryModel
				.getAmountFormulaOutput(
						amount, 
						principal, 
						interestRate, 
						time);

		if (amount == null) {
			Double fcs = (1 + interestRate * time);
			amount = principal * fcs;
			
			amountFormulaOutput.setAmount(amount);
		} else if (principal == null) {
			Double fcs = (1 + interestRate * time);
			Double fas = 1 / fcs;
			principal = amount * fas;
			
			amountFormulaOutput.setPrincipal(principal);
		} else if (interestRate == null) {
			interestRate = ((amount / principal) - 1) / time;
			
			amountFormulaOutput.setInterestRate(interestRate);
		} else if (time == null) {
			time = ((amount / principal) - 1) / interestRate;
			
			amountFormulaOutput.setTime(interestRate);
		} else {
			throw new NothingToBeCalculatedException();
		}

		return amountFormulaOutput;
	}
}