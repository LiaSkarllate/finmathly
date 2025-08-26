package dev.liaskarllate.finmathly.calculation.interest.compound.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompoundInterestFormulaOutputDTO {
    private BigDecimal interest;
    private BigDecimal presentValue;
    private BigDecimal interestRate;
    private BigDecimal time;
}