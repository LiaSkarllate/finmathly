package dev.liaskarllate.finmathly.dto.interest.simple;

public class ObjectFactoryDTO {
	public static FormulaOutputDTO getFormulaOutputDTO(
			Double interest,
			Double principal,
			Double interestRate,
			Double time) {
		return new FormulaOutputDTO(
				interest, 
				principal, 
				interestRate,
				time);
	}

	public static AmountFormulaOutputDTO getAmountFormulaOutputDTO(
			Double amount,
			Double principal,
			Double interestRate,
			Double time) {
		return new AmountFormulaOutputDTO(
				amount, 
				principal, 
				interestRate, 
				time);
	}
}
