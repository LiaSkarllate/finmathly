package dev.liaskarllate.finmathly.service.interest.simple;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.model.interest.simple.ObjectFactoryModel;
import dev.liaskarllate.finmathly.model.interest.simple.AmountFormulaOutput;
import dev.liaskarllate.finmathly.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.exception.TooManyMissingParametersException;

@Service
public class AmountFormulaService {
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