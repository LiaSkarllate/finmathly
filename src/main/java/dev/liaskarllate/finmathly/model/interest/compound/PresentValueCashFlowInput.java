package dev.liaskarllate.finmathly.model.interest.compound;

import java.util.List;

import dev.liaskarllate.finmathly.model.FlowInputOutput;

public class PresentValueCashFlowInput {
	public List<FlowInputOutput> flows;
	public Double interestRate;
	
	public PresentValueCashFlowInput(
			List<FlowInputOutput> flows, 
			Double interestRate) {
		this.flows = flows;
		this.interestRate = interestRate;
	}
	
	public List<FlowInputOutput> getFlows() {
		return flows;
	}
	
	public void setFlows(List<FlowInputOutput> flows) {
		this.flows = flows;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
}