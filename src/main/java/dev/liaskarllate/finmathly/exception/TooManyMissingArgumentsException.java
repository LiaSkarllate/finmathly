package dev.liaskarllate.finmathly.exception;

public class TooManyMissingArgumentsException extends InputNotValidException {
	private static final long serialVersionUID = 1L;
	private static final String message = "The number of missing arguments, %d, is wrong: Only %d of %d must be missing.";
	
	public TooManyMissingArgumentsException(int missingParametersCount, int expectedMissingParametersCount, int totalParameterCount) {
        super(String.format(message, missingParametersCount, expectedMissingParametersCount, totalParameterCount));
    }
}
