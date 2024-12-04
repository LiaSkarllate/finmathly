package dev.liaskarllate.finmathly.model.interest.compound;

public class ObjectFactoryModel {
	public static FormulaOutput getFormulaOutput(
			Double interest,
			Double presentValue, 
			Double interestRate,
			Double time) {
		return new FormulaOutput(
				interest, 
				presentValue, 
				interestRate, 
				time);
	}
	
	public static FutureValueFormulaOutput getFutureValueFormulaOutput(
			Double futureValue,
			Double presentValue,
			Double interestRate,
			Double time) {
		return new FutureValueFormulaOutput(
				futureValue, 
				presentValue, 
				interestRate, 
				time);
	}
}