package dev.liaskarllate.finmathly.interest.compound.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FutureValueFormulaOutputDTO {
    private BigDecimal futureValue;
    private BigDecimal presentValue;
    private BigDecimal interestRate;
    private BigDecimal time;
}