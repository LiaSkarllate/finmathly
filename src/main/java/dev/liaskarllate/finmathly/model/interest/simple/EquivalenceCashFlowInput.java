package dev.liaskarllate.finmathly.model.interest.simple;

import java.util.List;

import dev.liaskarllate.finmathly.model.FlowInputOutput;

public class EquivalenceCashFlowInput {
	 private List<FlowInputOutput> originalCashFlows;
     private List<FlowInputOutput> proposedCashFlows;
     private FlowInputOutput flowOfInterest;
     private Double focalTime;
     private Double interestRate;
     
     public EquivalenceCashFlowInput(
    		List<FlowInputOutput> originalCashFlows,
			List<FlowInputOutput> proposedCashFlows,
			FlowInputOutput flowOfInterest,
			Double focalTime,
			Double interestRate) {
		this.originalCashFlows = originalCashFlows;
		this.proposedCashFlows = proposedCashFlows;
		this.flowOfInterest = flowOfInterest;
		this.focalTime = focalTime;
		this.interestRate = interestRate;
	}
	
	public List<FlowInputOutput> getOriginalCashFlows() {
		return originalCashFlows;
	}
	
	public List<FlowInputOutput> getProposedCashFlows() {
		return proposedCashFlows;
	}
	
	public FlowInputOutput getEventOfInterest() {
		return flowOfInterest;
	}
	
	public Double getFocalTime() {
		return focalTime;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
}