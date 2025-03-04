package dev.liaskarllate.finmathly.dto.interest.compound;

import java.util.List;

import dev.liaskarllate.finmathly.dto.FlowInputDTO;
import dev.liaskarllate.finmathly.model.FlowInput;
import dev.liaskarllate.finmathly.model.ObjectFactoryModel;
import dev.liaskarllate.finmathly.model.interest.compound.PresentValueCashFlowInput;
import io.swagger.v3.oas.annotations.media.Schema;

public class PresentValueCashFlowInputDTO {
	@Schema(
		description = "a list of flows (a cash flow)",
		example = "[\n\t{\"value\" :15000.00, \"time\": 2},\n\t{\"value\": 40000.00, \"time\": 5},\n\t{\"value\": 50000.00, \"time\": 6},\n\t{\"value\": 70000.00, \"time\": 8}\n]")
	public List<FlowInputDTO> flows;
	
	@Schema(
		description = "the interest rate used for discounting the flows",
		example = "0.03")
	public Double interestRate;
	
	public PresentValueCashFlowInput toModel() {
		List<FlowInput> modelFlows = this.flows
            .stream()
            .map(FlowInputDTO::toModel)
            .toList();
		
		return ObjectFactoryModel.getPresentValueCashFlowInput(
            modelFlows, 
            this.interestRate);
	}
	
	public PresentValueCashFlowInputDTO(
			List<FlowInputDTO> flows, 
			Double interestRate) {
		this.flows = flows;
		this.interestRate = interestRate;
	}
	
	public List<FlowInputDTO> getFlows() {
		return flows;
	}
	
	public void setFlows(List<FlowInputDTO> flows) {
		this.flows = flows;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
}