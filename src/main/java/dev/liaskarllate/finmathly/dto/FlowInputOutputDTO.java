package dev.liaskarllate.finmathly.dto;

import dev.liaskarllate.finmathly.model.FlowInputOutput;
import dev.liaskarllate.finmathly.model.ObjectFactoryModel;

public class FlowInputOutputDTO {
	private Double value;
	private Double time;
	
	public FlowInputOutputDTO(
			Double value, 
			Double time) {
		this.value = value;
		this.time = time;
	}

	public FlowInputOutput toModel() {
		return ObjectFactoryModel.getFlowInputOutput(
				this.value,
				this.time);
	}
	
	public Double getValue() {
		return value;
	}
	
	public Double getTime() {
		return time;
	}
}
