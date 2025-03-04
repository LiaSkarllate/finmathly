package dev.liaskarllate.finmathly.dto;

import dev.liaskarllate.finmathly.model.FlowInput;
import dev.liaskarllate.finmathly.model.ObjectFactoryModel;
import io.swagger.v3.oas.annotations.media.Schema;

public class FlowInputDTO {
	@Schema(
		description =  "the value of the flow",
		example = "15000.00")
	private Double value;
	
	@Schema(
		description = "the time of the flow",
		example = "2")
	private Double time;
	
	public FlowInputDTO(
			Double value, 
			Double time) {
		this.value = value;
		this.time = time;
	}

	public FlowInput toModel() {
		return ObjectFactoryModel.getFlowInput(
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
