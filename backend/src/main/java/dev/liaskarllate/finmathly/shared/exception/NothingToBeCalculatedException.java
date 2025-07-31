package dev.liaskarllate.finmathly.shared.exception;

import java.io.Serial;

import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

public class NothingToBeCalculatedException extends InvalidInputException {
    @Serial
    private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "There is nothing to be calculated. All values have been provided.";

	public NothingToBeCalculatedException() {
        super(MESSAGE);
    }
}
