package dev.liaskarllate.finmathly.model;

public class ObjectFactoryModel {
	public static FlowInputOutput getFlowInputOutput(
			Double value, 
			Double time) {
		return new FlowInputOutput(
				value, 
				time);
	}
}
