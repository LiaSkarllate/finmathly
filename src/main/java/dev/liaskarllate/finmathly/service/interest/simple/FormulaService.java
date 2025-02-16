package dev.liaskarllate.finmathly.service.interest.simple;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.model.interest.simple.ObjectFactoryModel;
import dev.liaskarllate.finmathly.model.interest.simple.FormulaOutput;
import dev.liaskarllate.finmathly.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.exception.TooManyMissingArgumentsException;

/**
 * Service class for applying the simple interest formula.
 */
@Service
public class FormulaService {
	/**
     * Applies the simple interest formula to calculate the missing parameter.
     *
     * @param interest      the interest (can be null if it needs to be calculated).
     * @param principal     the principal (can be null if it needs to be calculated).
     * @param interestRate  the interest rate (as a decimal, e.g., 0.05 for 5%) (can be null if it needs to be calculated).
     * @param time          the time (can be null if it needs to be calculated).
     * @return A {@link FormulaOutput} object containing all values, including the calculated one.
	 */
	public FormulaOutput applyFormula(
			Double interest,
			Double principal, 
			Double interestRate, 
			Double time) {
		
		this.validateTheNumberOfParametersProvided(
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

	private void validateTheNumberOfParametersProvided(
			Double interest,
			Double principal, 
			Double interestRate, 
			Double time) {
		int missingParametersCount = 0;

		if (interest == null) missingParametersCount++;
		if (principal == null) missingParametersCount++;
		if (interestRate == null) missingParametersCount++;
		if (time == null) missingParametersCount++;

		if (missingParametersCount != 1) {
			throw new TooManyMissingArgumentsException(missingParametersCount, 1, 4);
		}
	}

	/**
     * Performs the calculation of the missing parameter using the simple interest formula.
     */
	private FormulaOutput calculate(
			Double interest, 
			Double principal, 
			Double interestRate, 
			Double time) {
		
		FormulaOutput formulaOutput = ObjectFactoryModel
				.getFormulaOutput(
						interest, 
						principal, 
						interestRate, 
						time);

		if (interest == null) {
			interest = principal * interestRate * time;
			
			formulaOutput.setInterest(interest);
		} else if (principal == null) {
			principal = interest / (interestRate * time);
			
			formulaOutput.setPrincipal(principal);
		} else if (interestRate == null) {
			interestRate = interest / (principal * time);
			
			formulaOutput.setInterestRate(interestRate);
		} else if (time == null) {
			time = interest / (principal * interestRate);
			
			formulaOutput.setTime(time);
		} else {
			throw new NothingToBeCalculatedException();
		}

		return formulaOutput;
	}
}