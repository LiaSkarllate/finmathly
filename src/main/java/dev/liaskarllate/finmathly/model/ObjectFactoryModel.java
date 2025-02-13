package dev.liaskarllate.finmathly.model;

import java.util.List;

import dev.liaskarllate.finmathly.model.interest.compound.PresentValueCashFlowInput;

public class ObjectFactoryModel {
	public static FlowInputOutput getFlowInputOutput(
			Double value, 
			Double time) {
		return new FlowInputOutput(
				value, 
				time);
	}
	
	public static PresentValueCashFlowInput getPresentValueCashFlowInput(
			List<FlowInputOutput> flows, 
			Double interestRate) {
		return new PresentValueCashFlowInput (
				flows, 
				interestRate);
	}
}