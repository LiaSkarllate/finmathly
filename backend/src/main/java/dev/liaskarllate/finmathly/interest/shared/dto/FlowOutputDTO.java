package dev.liaskarllate.finmathly.interest.shared.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FlowOutputDTO {
    @Schema(description = "the value of the flow", example = "15000.00")
    private BigDecimal value;

    @Schema(example = "0")
    private BigDecimal time;
}
