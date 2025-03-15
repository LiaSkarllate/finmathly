package dev.liaskarllate.finmathly.service.interest.compound;

import dev.liaskarllate.finmathly.model.interest.compound.CompoundInterestFormulaOutput;
import dev.liaskarllate.finmathly.model.ObjectFactoryModel;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.exception.TooManyMissingArgumentsException;

/**
 * Service class for applying the compound interest formula.
 */
@Service
public class CompoundInterestFormulaService {
	public CompoundInterestFormulaOutput applyFormula(
			Double interest,
			Double presentValue, 	
			Double interestRate, 
			Double time) {
		
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
			throw new TooManyMissingArgumentsException(missingParametersCount, 1, 4);
		}
	}

	private CompoundInterestFormulaOutput calculate(
			Double interest, 
			Double presentValue, 
			Double interestRate, 
			Double time) {
		
		CompoundInterestFormulaOutput formulaOutput = ObjectFactoryModel
				.getCompoundInterestFormulaOutput(
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