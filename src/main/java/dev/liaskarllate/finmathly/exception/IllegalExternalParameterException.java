package dev.liaskarllate.finmathly.exception;

public class IllegalExternalParameterException extends InputValidationException{
	private static final long serialVersionUID = 1L;
	private static final String message = "A external condition has not been fulfilled.";
	
	public IllegalExternalParameterException() {
		super(message);
	}
	
	public IllegalExternalParameterException(String message) {
		super(message);
	}
}
