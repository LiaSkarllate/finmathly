package dev.liaskarllate.finmathly.model;

public class FlowOutput {
	private Double value;
	private Double time;
	
	public FlowOutput(Double value, Double time) {
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
