package dev.liaskarllate.finmathly.service.interest.compound;

import dev.liaskarllate.finmathly.model.interest.compound.FutureValueFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.ObjectFactoryModel;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.exception.TooManyMissingParametersException;

/**
 * Service class for applying the compound interest future value formula.
 */
@Service
public class FutureValueFormulaService {
	/**
     * Applies the compound interest future value formula to calculate the future value, the missing parameter in this case.
     *
     * @param presentValue  the present (cannot be null).
     * @param interestRate  the interest rate (as a decimal, e.g., 0.05 for 5%) (cannot be null).
     * @param time          the time (cannot be null).
     * @return A {@link FutureValueFormulaOutput} object containing all values, including the calculated one.
     */
	public FutureValueFormulaOutput applyFormula(
			Double presentValue,
			Double interestRate,
			Double time) {
		
		return this.applyFormula(
				null, 
				presentValue, 
				interestRate, 
				time);
	}

	/**
     * Applies the compound interest future value formula to calculate the missing parameter.
     *
     * @param futureValue   the future value (can be null if it needs to be calculated).
     * @param presentValue  the present value (can be null if it needs to be calculated).
     * @param interestRate  the interest rate (as a decimal, e.g., 0.05 for 5%) (can be null if it needs to be calculated).
     * @param time          the time (can be null if it needs to be calculated).
     * @return A {@link FutureValueFormulaOutput} object containing all values, including the calculated one.
     */
	public FutureValueFormulaOutput applyFormula(
			Double futureValue,
			Double presentValue,
			Double interestRate,
			Double time) {
		
		this.validateTheNumberOfParametersProvided(
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

	private void validateTheNumberOfParametersProvided(
			Double futureValue,
			Double presentValue,
			Double interestRate,
			Double time) {
		int missingParametersCount = 0;

        if (futureValue == null) missingParametersCount++;
        if (presentValue == null) missingParametersCount++;
        if (interestRate == null) missingParametersCount++;
        if (time == null) missingParametersCount++;

		if (missingParametersCount != 1) {
			throw new TooManyMissingParametersException(missingParametersCount, 1, 4);
		}
	}

	/**
     * Performs the calculation of the missing parameter using the compound interest future value formula.
     */
	private FutureValueFormulaOutput calculate(
			Double futureValue,
			Double presentValue,
			Double interestRate,
			Double time) {
		
		FutureValueFormulaOutput futureValueFormulaOutput = ObjectFactoryModel
				.getFutureValueFormulaOutput(
						futureValue,
						presentValue, 
						interestRate, 
						time);

		if (futureValue == null) {
			Double fcc = Math.pow((1 + interestRate), time);
			futureValue = presentValue * fcc;
					
			futureValueFormulaOutput.setFutureValue(futureValue);
		} else if (presentValue == null) {
			Double fcc = Math.pow((1 + interestRate), time);
			Double fac = 1 / fcc;
			presentValue = futureValue * fac;
					
			futureValueFormulaOutput.setPresentValue(presentValue);
		} else if (interestRate == null) {
			interestRate = Math.pow((futureValue / presentValue), (1 / time)) - 1;
					
			futureValueFormulaOutput.setInterestRate(interestRate);
		} else if (time == null) {
			time = (Math.log((futureValue / presentValue))) / (Math.log(1 + interestRate));
					
			futureValueFormulaOutput.setTime(time);
		} else {
			throw new NothingToBeCalculatedException();
		}

		return futureValueFormulaOutput;
	}
}