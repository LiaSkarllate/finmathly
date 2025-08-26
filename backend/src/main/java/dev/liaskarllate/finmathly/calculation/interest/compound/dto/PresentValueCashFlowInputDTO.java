package dev.liaskarllate.finmathly.calculation.interest.compound.dto;

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
public class PresentValueCashFlowInputDTO {
    @Schema(description = "a list of flows (a cash flow)", example = "[\n\t{\"value\" :15000.00, \"time\": 2},\n\t{\"value\": 40000.00, \"time\": 5},\n\t{\"value\": 50000.00, \"time\": 6},\n\t{\"value\": 70000.00, \"time\": 8}\n]")
    public List<FlowInputDTO> flows;

    @Schema(description = "the interest rate used for discounting the flows", example = "0.03")
    public BigDecimal interestRate;
}