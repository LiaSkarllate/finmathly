package dev.liaskarllate.finmathly.dto;

import dev.liaskarllate.finmathly.dto.interest.EquivalentInterestRateOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.compound.CompoundInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.compound.FutureValueFormulaOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.simple.AmountFormulaOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.simple.SimpleInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;

public class ObjectFactoryDTO {
	private ObjectFactoryDTO() {
		
	}

	public static ThrownExceptionDTO getThrownExceptionDTO(String message) {
		return new ThrownExceptionDTO(message);
	}
	
	public static FlowInputDTO getFlowInputDTO(
			Double value, 
			Double time) {
		return new FlowInputDTO(
				value, 
				time);
	}
	
	public static FlowOutputDTO getFlowOutputDTO(
			Double value, 
			Double time) {
		return new FlowOutputDTO(
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

	public static FutureValueFormulaOutputDTO getFutureValueFormulaOutputDTO(
			Double futureValue,
			Double presentValue,
			Double interestRate,
			Double time) {
		return new FutureValueFormulaOutputDTO(futureValue, presentValue, interestRate, time);
	}

	public static CompoundInterestFormulaOutputDTO getCompoundInterestFormulaOutputDTO(
			Double interest,
			Double presentValue,
			Double interestRate,
			Double time) {
		return new CompoundInterestFormulaOutputDTO(
				interest, 
				presentValue,
				interestRate, 
				time);
	}

	public static SimpleInterestFormulaOutputDTO getSimpleInterestFormulaOutputDTO(
			Double interest,
			Double principal,
			Double interestRate,
			Double time) {
		return new SimpleInterestFormulaOutputDTO(
				interest, 
				principal, 
				interestRate,
				time);
	}

	public static AmountFormulaOutputDTO getAmountFormulaOutputDTO(
			Double amount,
			Double principal,
			Double interestRate,
			Double time) {
		return new AmountFormulaOutputDTO(
				amount, 
				principal, 
				interestRate, 
				time);
	}
}