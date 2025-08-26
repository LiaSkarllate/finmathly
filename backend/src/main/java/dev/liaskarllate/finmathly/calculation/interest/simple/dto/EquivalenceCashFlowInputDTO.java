package dev.liaskarllate.finmathly.calculation.interest.simple.dto;

import java.math.BigDecimal;

import java.util.List;

import dev.liaskarllate.finmathly.calculation.interest.shared.dto.FlowInputDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EquivalenceCashFlowInputDTO {
    @Schema(description = "the list of flows from the proposed cash flow", example = "[\n\t{\"value\": 50000.00, \"time\": 4},\n\t{\"value\": 80000.00, \"time\": 8}\n]")
    private List<FlowInputDTO> originalFlows;

    @Schema(description = "the list of flows from the original cash flow", example = "[\n\t{\"value\": 10000.00, \"time\": 0},\n\t{\"value\": 30000.00, \"time\": 6}\n]")
    private List<FlowInputDTO> proposedFlows;

    @Schema(description = "the target flow from the proposed cash flow whose value needs to be calculated", example = "{\"value\": 0, \"time\": 12}")
    private FlowInputDTO targetFlow;

    @Schema(description = "the time reference for adjusting flows", example = "0")
    private BigDecimal focalTime;

    @Schema(description = "the interest rate", example = "0.02")
    private BigDecimal interestRate;
}