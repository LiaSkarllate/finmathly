package dev.liaskarllate.finmathly.dto.interest.compound;

public class FormulaOutputDTO {
	private Double interest;
	private Double presentValue;
	private Double interestRate;
	private Double time;

	public FormulaOutputDTO(
			Double interest, 
			Double presentValue, 
			Double interestRate, 
			Double time) {
		this.interest = interest;
		this.presentValue = presentValue;
		this.interestRate = interestRate;
		this.time = time;
	}

	public Double getInterest() {
		return interest;
	}

	public Double getPresentValue() {
		return presentValue;
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

	public void setPresentValue(Double presentValue) {
		this.presentValue = presentValue;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public void setTime(Double time) {
		this.time = time;
	}
}