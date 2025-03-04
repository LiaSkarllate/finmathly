package dev.liaskarllate.finmathly.dto;

import dev.liaskarllate.finmathly.model.FlowOutput;
import dev.liaskarllate.finmathly.model.ObjectFactoryModel;
import io.swagger.v3.oas.annotations.media.Schema;

public class FlowOutputDTO {
	@Schema(
		description = "the value of the flow",
		example = "15000.00")
	private Double value;
	
	@Schema(example = "0")
	private Double time;
	
	public FlowOutputDTO(
			Double value, 
			Double time) {
		this.value = value;
		this.time = time;
	}

	public FlowOutput toModel() {
		return ObjectFactoryModel.getFlowOutput(
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
