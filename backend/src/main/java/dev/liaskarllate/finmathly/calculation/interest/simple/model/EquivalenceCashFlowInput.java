package dev.liaskarllate.finmathly.calculation.interest.simple.model;

import java.util.List;

import dev.liaskarllate.finmathly.calculation.interest.shared.model.FlowInput;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EquivalenceCashFlowInput {
    private List<FlowInput> originalFlows;
    private List<FlowInput> proposedFlows;
    private FlowInput targetFlow;
    private BigDecimal focalTime;
    private BigDecimal interestRate;
}