package dev.liaskarllate.finmathly.dto;

public class ObjectFactoryDTO {
	public static ThrownExceptionDTO getThrownExceptionDTO(String message) {
		return new ThrownExceptionDTO(message);
	}
	
	public static FlowInputOutputDTO getFlowInputOutputDTO(
			Double value, 
			Double time) {
		return new FlowInputOutputDTO(
				value, 
				time);
	}
}
