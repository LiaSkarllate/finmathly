package dev.liaskarllate.finmathly.dto.interest.simple;

public class AmountFormulaOutputDTO{
	private Double amount;
	private Double principal; 
	private Double interestRate;
	private Double time;
	
	public AmountFormulaOutputDTO(
			Double amount, 
			Double principal, 
			Double interestRate, 
			Double time) {
		this.amount = amount;
		this.principal = principal;
		this.interestRate = interestRate;
		this.time = time;
	}

	public Double getAmount() {
		return amount;
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

	public void setAmount(Double amount) {
		this.amount = amount;
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