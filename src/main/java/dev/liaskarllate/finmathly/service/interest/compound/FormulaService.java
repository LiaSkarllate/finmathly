package dev.liaskarllate.finmathly.service.interest.compound;

import dev.liaskarllate.finmathly.model.interest.compound.FormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.ObjectFactoryModel;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.exception.TooManyMissingParametersException;

/**
 * Service class for applying the compound interest formula.
 */
@Service
public class FormulaService {
	/**
     * Applies the compound interest formula to calculate the missing parameter.
     *
     * @param interest      the interest (can be null if it needs to be calculated).
     * @param presentValue  the present value (can be null if it needs to be calculated).
     * @param interestRate  the interest rate (as a decimal, e.g., 0.05 for 5%) (can be null if it needs to be calculated).
     * @param time          the time (can be null if it needs to be calculated).
     * @return A {@link FormulaOutput} object containing all values, including the calculated one.
     */
	public FormulaOutput applyFormula(
			Double interest,
			Double presentValue, 
			Double interestRate, 
			Double time) {
		
		this.validateTheNumberOfParametersProvided(
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
	
	private void validateTheNumberOfParametersProvided(
			Double interest,
			Double presentValue, 
			Double interestRate, 
			Double time) {
		int missingParametersCount = 0;

		if (interest == null) missingParametersCount++;
		if (presentValue == null) missingParametersCount++;
		if (interestRate == null) missingParametersCount++;
		if (time == null) missingParametersCount++;

		if (missingParametersCount != 1) {
			throw new TooManyMissingParametersException(missingParametersCount, 1, 4);
		}
	}

	/**
     * Performs the calculation of the missing parameter using the compound interest formula.
     */
	private FormulaOutput calculate(
			Double interest, 
			Double presentValue, 
			Double interestRate, 
			Double time) {
		
		FormulaOutput formulaOutput = ObjectFactoryModel
				.getFormulaOutput(
						interest, 
						presentValue, 
						interestRate, 
						time);
		
		if (interest == null) {
			interest = presentValue * Math.pow((1 + interestRate), time);
			
			formulaOutput.setInterest(interest);
		} else if (presentValue == null) {
            presentValue = interest / Math.pow((1 + interestRate), time);
            
			formulaOutput.setPresentValue(presentValue);
		} else if (interestRate == null) {
            interestRate = Math.pow((interest / presentValue), (1 / time)) - 1;
            
			formulaOutput.setInterestRate(interestRate);
		} else if (time == null) {
            time = Math.log(interest / presentValue) / Math.log(1 + interestRate);
            
			formulaOutput.setTime(time);
		} else {
			throw new NothingToBeCalculatedException();
		}

		return formulaOutput;
	}
}