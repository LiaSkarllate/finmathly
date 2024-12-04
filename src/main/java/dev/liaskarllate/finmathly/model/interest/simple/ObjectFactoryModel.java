package dev.liaskarllate.finmathly.model.interest.simple;

import java.util.List;

import dev.liaskarllate.finmathly.model.FlowInputOutput;

public class ObjectFactoryModel {
	public static FormulaOutput getFormulaOutput(
			Double interest, 
			Double principal,
			Double interestRate,
			Double time) {
		return new FormulaOutput(
				interest, 
				principal, 
				interestRate,
				time);
	}
	
	public static AmountFormulaOutput getAmountFormulaOutput(
			Double amount, 
			Double principal, 
			Double interestRate, 
			Double time) {
		return new AmountFormulaOutput(
				amount, 
				principal, 
				interestRate, 
				time);
	}
	
	public static EquivalenceCashFlowInput getEquivalenceCashFlowInput(
			List<FlowInputOutput> originalCashFlows,
			List<FlowInputOutput> proposedCashFlows,
			FlowInputOutput flowOfInterest,
			Double focalTime,
			Double interestRate) {
		return new EquivalenceCashFlowInput(
				originalCashFlows, 
				proposedCashFlows, 
				flowOfInterest, 
				focalTime, 
				interestRate);
	}
}
