package dev.liaskarllate.finmathly.dto.interest.compound;

public class ObjectFactoryDTO {
	
	public static FutureValueFormulaOutputDTO getFutureValueFormulaOutputDTO(
			Double futureValue,
			Double presentValue,
			Double interestRate,
			Double time) {
		return new FutureValueFormulaOutputDTO(futureValue, presentValue, interestRate, time);
	}

	public static FormulaOutputDTO getFormulaOutputDTO(
			Double interest,
			Double presentValue,
			Double interestRate,
			Double time) {
		return new FormulaOutputDTO(
				interest, 
				presentValue,
				interestRate, 
				time);
	}
}
