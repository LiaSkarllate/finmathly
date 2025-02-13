package dev.liaskarllate.finmathly.dto.interest.simple;

import java.util.List;

import dev.liaskarllate.finmathly.dto.FlowInputOutputDTO;
import dev.liaskarllate.finmathly.model.FlowInputOutput;
import dev.liaskarllate.finmathly.model.interest.simple.EquivalenceCashFlowInput;
import dev.liaskarllate.finmathly.model.interest.simple.ObjectFactoryModel;

public class EquivalenceCashFlowInputDTO {
	 private List<FlowInputOutputDTO> originalCashFlows;
     private List<FlowInputOutputDTO> proposedCashFlows;
     private FlowInputOutputDTO flowOfInterest;
     private Double focalTime;
     private Double interestRate;
     
     public EquivalenceCashFlowInputDTO(
    		List<FlowInputOutputDTO> originalFlows,
			List<FlowInputOutputDTO> proposedFlows,
			FlowInputOutputDTO flowOfInterest,
			Double focalTime,
			Double interestRate) {
		this.originalCashFlows = originalFlows;
		this.proposedCashFlows = proposedFlows;
		this.flowOfInterest = flowOfInterest;
		this.focalTime = focalTime;
		this.interestRate = interestRate;
	}
     
    public EquivalenceCashFlowInput toModel() {
    	List<FlowInputOutput> originalFlows = this.originalCashFlows
    			.stream()
    			.map(flow -> flow.toModel())
    			.toList();
    	
		List<FlowInputOutput> proposedFlows = this.proposedCashFlows
    			.stream()
    			.map(flow -> flow.toModel())
    			.toList();
    	
    	return ObjectFactoryModel.getEquivalenceCashFlowInput (
    			originalFlows,
    			proposedFlows,
    			this.flowOfInterest.toModel(),
    			this.focalTime,
    			this.interestRate);
    }
	
	public List<FlowInputOutputDTO> getOriginalCashFlows() {
		return originalCashFlows;
	}
	
	public List<FlowInputOutputDTO> getProposedCashFlows() {
		return proposedCashFlows;
	}
	
	public FlowInputOutputDTO getEventOfInterest() {
		return flowOfInterest;
	}
	
	public Double getFocalTime() {
		return focalTime;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
}