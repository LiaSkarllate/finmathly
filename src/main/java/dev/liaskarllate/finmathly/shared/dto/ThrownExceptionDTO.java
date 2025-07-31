package dev.liaskarllate.finmathly.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ThrownExceptionDTO {
    @Schema(example = "We're sorry, but an unexpected issue occurred. Please try again later. If necessary, contact our support team.")
    private String message;
}