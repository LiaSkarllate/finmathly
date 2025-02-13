package dev.liaskarllate.finmathly.model.interest.compound;

import java.util.List;

import dev.liaskarllate.finmathly.model.FlowInputOutput;

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
	
	public static PresentValueCashFlowInput getPresentValueCashFlowInput(
			List<FlowInputOutput> flows, 
			Double interestRate) {
		return new PresentValueCashFlowInput(
				flows,
				interestRate);
	}
}