package dev.liaskarllate.finmathly.dto;

public class ThrownExceptionDTO {
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