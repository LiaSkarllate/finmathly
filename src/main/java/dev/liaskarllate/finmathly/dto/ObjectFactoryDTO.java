package dev.liaskarllate.finmathly.dto;

import dev.liaskarllate.finmathly.dto.interest.EquivalentInterestRateOutputDTO;
import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;

public class ObjectFactoryDTO extends dev.liaskarllate.finmathly.dto.interest.simple.ObjectFactoryDTO {
	
	public static ThrownExceptionDTO getThrownExceptionDTO(String message) {
		return new ThrownExceptionDTO(message);
	}
	
	public static FlowInputOutputDTO getFlowInputOutputDTO(
			Double value, 
			Double time) {
		return new FlowInputOutputDTO(
				value, 
				time);
	}
	
	public static EquivalentInterestRateOutputDTO getEquivalentInterestRateOutputDTO(
			Double interestRate,
			CapitalizationPeriod capitalizationPeriod) {
		return new EquivalentInterestRateOutputDTO(
				interestRate,
				capitalizationPeriod);
	}
}
