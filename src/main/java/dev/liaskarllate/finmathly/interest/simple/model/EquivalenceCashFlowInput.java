package dev.liaskarllate.finmathly.interest.simple.model;

import java.util.List;

import java.math.BigDecimal;

import dev.liaskarllate.finmathly.interest.shared.model.FlowInput;
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