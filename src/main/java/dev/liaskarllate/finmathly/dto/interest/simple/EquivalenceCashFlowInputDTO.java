package dev.liaskarllate.finmathly.dto.interest.simple;

import java.util.List;

import dev.liaskarllate.finmathly.dto.FlowInputDTO;
import dev.liaskarllate.finmathly.model.FlowInput;
import dev.liaskarllate.finmathly.model.interest.simple.EquivalenceCashFlowInput;
import io.swagger.v3.oas.annotations.media.Schema;
import dev.liaskarllate.finmathly.model.ObjectFactoryModel;

public class EquivalenceCashFlowInputDTO {
	@Schema(
		description = "the list of flows from the proposed cash flow",
		example = "[\n\t{\"value\": 50000.00, \"time\": 4},\n\t{\"value\": 80000.00, \"time\": 8}\n]")
    private List<FlowInputDTO> originalFlows;

    @Schema(
		description = "the list of flows from the original cash flow",
		example = "[\n\t{\"value\": 10000.00, \"time\": 0},\n\t{\"value\": 30000.00, \"time\": 6}\n]")
    private List<FlowInputDTO> proposedFlows;
    
    @Schema(
		description = "the target flow from the proposed cash flow whose value needs to be calculated",
		example = "{\"value\": 0, \"time\": 12}")
    private FlowInputDTO targetFlow;
    
    @Schema(
		description = "the time reference for adjusting flows",
		example = "0")
    private Double focalTime;

    @Schema(
		description = "the interest rate",
		example = "0.02")
    private Double interestRate;
     
     public EquivalenceCashFlowInputDTO(
    		List<FlowInputDTO> originalFlows,
			List<FlowInputDTO> proposedFlows,
			FlowInputDTO targetFlow,
			Double focalTime,
			Double interestRate) {
		this.originalFlows = originalFlows;
		this.proposedFlows = proposedFlows;
		this.targetFlow = targetFlow;
		this.focalTime = focalTime;
		this.interestRate = interestRate;
	}
     
    public EquivalenceCashFlowInput toModel() {
    	List<FlowInput> modelOriginalFlows = this.originalFlows
    			.stream()
    			.map(FlowInputDTO::toModel)
    			.toList();
    	
		List<FlowInput> modelProposedFlows = this.proposedFlows
    			.stream()
    			.map(FlowInputDTO::toModel)
    			.toList();
    	
    	return ObjectFactoryModel.getEquivalenceCashFlowInput (
    			modelOriginalFlows,
    			modelProposedFlows,
    			this.targetFlow.toModel(),
    			this.focalTime,
    			this.interestRate);
    }
	
	public List<FlowInputDTO> getOriginalFlows() {
		return originalFlows;
	}
	
	public List<FlowInputDTO> getProposedFlows() {
		return proposedFlows;
	}
	
	public FlowInputDTO getTargetFlow() {
		return targetFlow;
	}
	
	public Double getFocalTime() {
		return focalTime;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
}