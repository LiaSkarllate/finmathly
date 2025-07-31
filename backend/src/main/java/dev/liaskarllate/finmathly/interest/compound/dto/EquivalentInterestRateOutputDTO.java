package dev.liaskarllate.finmathly.interest.compound.dto;

import java.math.BigDecimal;

import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EquivalentInterestRateOutputDTO{
	private BigDecimal interestRate;
	private CapitalizationPeriod capitalizationPeriod;
}