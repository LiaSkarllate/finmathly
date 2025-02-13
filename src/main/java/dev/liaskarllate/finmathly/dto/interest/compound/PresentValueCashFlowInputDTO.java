package dev.liaskarllate.finmathly.dto.interest.compound;

import java.util.List;

import dev.liaskarllate.finmathly.dto.FlowInputOutputDTO;
import dev.liaskarllate.finmathly.model.FlowInputOutput;
import dev.liaskarllate.finmathly.model.ObjectFactoryModel;
import dev.liaskarllate.finmathly.model.interest.compound.PresentValueCashFlowInput;

public class PresentValueCashFlowInputDTO {
	public List<FlowInputOutputDTO> flows;
	public Double interestRate;
	
	public PresentValueCashFlowInput toModel() {
		List<FlowInputOutput> flows = this.flows
				.stream()
    			.map(flow -> flow.toModel())
    			.toList();
		
		return ObjectFactoryModel.getPresentValueCashFlowInput(
				flows, 
				this.interestRate);
	}
	
	public PresentValueCashFlowInputDTO(
			List<FlowInputOutputDTO> flows, 
			Double interestRate) {
		this.flows = flows;
		this.interestRate = interestRate;
	}
	
	public List<FlowInputOutputDTO> getFlows() {
		return flows;
	}
	
	public void setFlows(List<FlowInputOutputDTO> flows) {
		this.flows = flows;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
}