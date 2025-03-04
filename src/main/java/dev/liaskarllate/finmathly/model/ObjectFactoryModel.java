package dev.liaskarllate.finmathly.model;

import java.util.List;

import dev.liaskarllate.finmathly.model.interest.compound.CompoundInterestFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.FutureValueFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.PresentValueCashFlowInput;
import dev.liaskarllate.finmathly.model.interest.simple.AmountFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.simple.EquivalenceCashFlowInput;
import dev.liaskarllate.finmathly.model.interest.simple.SimpleInterestFormulaOutput;

public class ObjectFactoryModel {
	private ObjectFactoryModel() {

	}
	
	public static FlowInput getFlowInput(
			Double value, 
			Double time) {
		return new FlowInput(
				value, 
				time);
	}
	
	public static FlowOutput getFlowOutput(
			Double value, 
			Double time) {
		return new FlowOutput(
				value, 
				time);
	}
	
	public static PresentValueCashFlowInput getPresentValueCashFlowInput(
			List<FlowInput> flows, 
			Double interestRate) {
		return new PresentValueCashFlowInput (
				flows, 
				interestRate);
	}

	public static SimpleInterestFormulaOutput getSimpleInterestFormulaOutput(
			Double interest,
			Double principal, 
			Double interestRate,
			Double time) {
		return new SimpleInterestFormulaOutput(
				interest, 
				principal, 
				interestRate, 
				time);
	}

	public static CompoundInterestFormulaOutput getCompoundInterestFormulaOutput(
			Double interest,
			Double presentValue, 
			Double interestRate,
			Double time) {
		return new CompoundInterestFormulaOutput(
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
			List<FlowInput> originalFlows,
			List<FlowInput> proposedFlows,
			FlowInput targetFlow,
			Double focalTime,
			Double interestRate) {
		return new EquivalenceCashFlowInput(
				originalFlows, 
				proposedFlows, 
				targetFlow, 
				focalTime, 
				interestRate);
	}
}