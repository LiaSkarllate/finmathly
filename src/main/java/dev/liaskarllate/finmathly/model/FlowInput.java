package dev.liaskarllate.finmathly.model;

public class FlowInput {
	private Double value;
	private Double time;
	
	public FlowInput(Double value, Double time) {
		this.value = value;
		this.time = time;
	}

	public Double getValue() {
		return value;
	}
	
	public Double getTime() {
		return time;
	}
}
