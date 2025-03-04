package dev.liaskarllate.finmathly.exception;

import java.io.Serial;

public class TooManyMissingArgumentsException extends InputNotValidException {
    @Serial
    private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The number of missing arguments, %d, is wrong: Only %d of %d must be missing.";
	
	public TooManyMissingArgumentsException(int missingParametersCount, int expectedMissingParametersCount, int totalParameterCount) {
        super(MESSAGE.formatted(missingParametersCount, expectedMissingParametersCount, totalParameterCount));
    }
}
