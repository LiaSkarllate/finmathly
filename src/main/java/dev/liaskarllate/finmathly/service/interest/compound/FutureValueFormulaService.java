package dev.liaskarllate.finmathly.service.interest.compound;

import dev.liaskarllate.finmathly.model.interest.compound.FutureValueFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.ObjectFactoryModel;
import dev.liaskarllate.finmathly.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.exception.TooManyMissingParametersException;

public class FutureValueFormulaService {

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