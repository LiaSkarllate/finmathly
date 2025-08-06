package dev.liaskarllate.finmathly.interest.simple.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AmountFormulaOutputDTO {
    private BigDecimal amount;
    private BigDecimal principal;
    private BigDecimal interestRate;
    private BigDecimal time;
}