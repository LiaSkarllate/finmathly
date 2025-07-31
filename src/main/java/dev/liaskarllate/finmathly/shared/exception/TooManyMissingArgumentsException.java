package dev.liaskarllate.finmathly.shared.exception;

import java.io.Serial;

import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

public class TooManyMissingArgumentsException extends InvalidInputException {
    @Serial
    private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The number of missing arguments, %d, is wrong: Only %d of %d must be missing.";
	
	public TooManyMissingArgumentsException(int missingParametersCount, int expectedMissingParametersCount, int totalParameterCount) {
        super(MESSAGE.formatted(missingParametersCount, expectedMissingParametersCount, totalParameterCount));
    }
}
