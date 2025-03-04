package dev.liaskarllate.finmathly.model.interest.simple;

import java.util.List;

import dev.liaskarllate.finmathly.model.FlowInput;

public class EquivalenceCashFlowInput {
	private List<FlowInput> originalFlows;
    private List<FlowInput> proposedFlows;
    private FlowInput targetFlow;
    private Double focalTime;
    private Double interestRate;
     
    public EquivalenceCashFlowInput(
            List<FlowInput> originalFlows,
			List<FlowInput> proposedFlows,
			FlowInput targetFlow,
			Double focalTime,
			Double interestRate) {
		this.originalFlows = originalFlows;
		this.proposedFlows = proposedFlows;
		this.targetFlow = targetFlow;
		this.focalTime = focalTime;
		this.interestRate = interestRate;
	}
	
	public List<FlowInput> getOriginalFlows() {
		return originalFlows;
	}
	
	public List<FlowInput> getProposedFlows() {
		return proposedFlows;
	}
	
	public FlowInput getTargetFlow() {
		return targetFlow;
	}
	
	public Double getFocalTime() {
		return focalTime;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
}