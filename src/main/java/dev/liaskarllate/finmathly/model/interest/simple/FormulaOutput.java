package dev.liaskarllate.finmathly.model.interest.simple;

import dev.liaskarllate.finmathly.dto.interest.simple.FormulaOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.simple.ObjectFactoryDTO;

public class FormulaOutput {
	private Double interest;
	private Double principal;
	private Double interestRate;
	private Double time;
	
	public FormulaOutputDTO toDTO() {
		return ObjectFactoryDTO.getFormulaOutputDTO(
				interest, 
				principal,
				interestRate,
				time);
	}
	
	public FormulaOutput(
			Double interest, 
			Double principal,
			Double interestRate,
			Double time) {
		this.interest = interest;
		this.principal = principal;
		this.interestRate = interestRate;
		this.time = time;
	}

	public Double getInterest() {
		return interest;
	}

	public Double getPrincipal() {
		return principal;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public Double getTime() {
		return time;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public void setTime(Double time) {
		this.time = time;
	}
}