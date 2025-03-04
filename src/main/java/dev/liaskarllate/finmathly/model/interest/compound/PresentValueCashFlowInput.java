package dev.liaskarllate.finmathly.model.interest.compound;

import java.util.List;

import dev.liaskarllate.finmathly.model.FlowInput;

public class PresentValueCashFlowInput {
	private List<FlowInput> flows;
	private Double interestRate;
	
	public PresentValueCashFlowInput(
			List<FlowInput> flows, 
			Double interestRate) {
		this.flows = flows;
		this.interestRate = interestRate;
	}
	
	public List<FlowInput> getFlows() {
		return flows;
	}
	
	public void setFlows(List<FlowInput> flows) {
		this.flows = flows;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
}