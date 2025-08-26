package dev.liaskarllate.finmathly.calculation.interest.shared.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FlowOutput {
    private BigDecimal value;
    private BigDecimal time;
}
