package dev.liaskarllate.finmathly.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ThrownExceptionDTO {
    @Schema(example = "We're sorry, but an unexpected issue occurred. Please try again later. If necessary, contact our support team.")
	private String message;

	public ThrownExceptionDTO(String exceptionMessage) {
		this.message = exceptionMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}