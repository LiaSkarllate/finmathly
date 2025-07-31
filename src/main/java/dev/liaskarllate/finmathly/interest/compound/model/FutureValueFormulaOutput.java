package dev.liaskarllate.finmathly.interest.compound.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FutureValueFormulaOutput {
    private BigDecimal futureValue;
    private BigDecimal presentValue;
    private BigDecimal interestRate;
    private BigDecimal time;
}