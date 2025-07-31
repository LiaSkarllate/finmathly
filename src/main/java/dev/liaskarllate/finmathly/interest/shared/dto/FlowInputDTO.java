package dev.liaskarllate.finmathly.interest.shared.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FlowInputDTO {
	@Schema(
		description =  "the value of the flow",
		example = "15000.00")
	private BigDecimal value;
	
	@Schema(
		description = "the time of the flow",
		example = "2")
	private BigDecimal time;
}
