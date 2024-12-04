package dev.liaskarllate.finmathly.dto.interest.compound;

public class FutureValueFormulaOutputDTO{
	private Double futureValue; 
	private Double presentValue; 
	private Double interestRate;
	private Double time;
	
	public FutureValueFormulaOutputDTO(
			Double futureValue,
			Double presentValue,
			Double interestRate,
			Double time) {
		this.futureValue = futureValue;
		this.presentValue = presentValue;
		this.interestRate = interestRate;
		this.time = time;
	}

	public Double getFutureValue() {
		return futureValue;
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

	public void setFutureValue(Double futureValue) {
		this.futureValue = futureValue;
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