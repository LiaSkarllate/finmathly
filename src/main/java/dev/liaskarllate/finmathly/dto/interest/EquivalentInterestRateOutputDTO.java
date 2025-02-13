package dev.liaskarllate.finmathly.dto.interest;

import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;

public class EquivalentInterestRateOutputDTO{
	private Double interestRate;
	private CapitalizationPeriod capitalizationPeriod;
	
	public EquivalentInterestRateOutputDTO(Double interestRate, CapitalizationPeriod capitalizationPeriod) {
		this.interestRate = interestRate;
		this.capitalizationPeriod = capitalizationPeriod;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public CapitalizationPeriod getInterestRatePeriod() {
		return capitalizationPeriod;
	}

	public void setInterestRatePeriod(CapitalizationPeriod capitalizationPeriod) {
		this.capitalizationPeriod = capitalizationPeriod;
	}
}