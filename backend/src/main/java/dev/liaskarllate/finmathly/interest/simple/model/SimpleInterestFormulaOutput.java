package dev.liaskarllate.finmathly.interest.simple.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimpleInterestFormulaOutput {
	private BigDecimal interest;
	private BigDecimal principal;
	private BigDecimal interestRate;
	private BigDecimal time;
}