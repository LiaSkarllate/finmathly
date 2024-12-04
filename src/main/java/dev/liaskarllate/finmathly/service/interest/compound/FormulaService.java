package dev.liaskarllate.finmathly.service.interest.compound;

import dev.liaskarllate.finmathly.model.interest.compound.FormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.ObjectFactoryModel;
import dev.liaskarllate.finmathly.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.exception.TooManyMissingParametersException;

public class FormulaService {
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
		
		// TODO: Terminar a implementação dos possíveis uso da fórmula de juros compostos: 
			// Vide a página 19 do livro-referência.
		if (interest == null) {
			// interest = 
			
			formulaOutput.setInterest(interest);
		} else if (presentValue == null) {
			// presentValue =
			
			formulaOutput.setPresentValue(presentValue);
		} else if (interestRate == null) {
			// interestRate = 
			
			formulaOutput.setInterestRate(interestRate);
		} else if (time == null) {
			// time = interest 
			
			formulaOutput.setTime(time);
		} else {
			throw new NothingToBeCalculatedException();
		}

		return formulaOutput;
	}
}