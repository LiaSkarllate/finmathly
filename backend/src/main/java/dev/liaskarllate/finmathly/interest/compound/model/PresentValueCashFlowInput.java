package dev.liaskarllate.finmathly.interest.compound.model;

import java.math.BigDecimal;

import java.util.List;

import dev.liaskarllate.finmathly.interest.shared.model.FlowInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PresentValueCashFlowInput {
    private List<FlowInput> flows;
    private BigDecimal interestRate;
}