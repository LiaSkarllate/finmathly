package dev.liaskarllate.finmathly.exception;

public class TooManyMissingParametersException extends InputValidationException{
	private static final long serialVersionUID = 1L;
	private static final String message = "The number of missing parameters, %d, is wrong: Only %d of %d must be missing.";
	
	public TooManyMissingParametersException(int missingParametersCount, int expectedMissingParametersCount, int totalParameterCount) {
        super(String.format(message, missingParametersCount, expectedMissingParametersCount, totalParameterCount));
    }
}
