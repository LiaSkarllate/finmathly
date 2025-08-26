package dev.liaskarllate.finmathly.calculation.interest.simple.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimpleInterestFormulaOutputDTO {
    private BigDecimal interest;
    private BigDecimal principal;
    private BigDecimal interestRate;
    private BigDecimal time;
}