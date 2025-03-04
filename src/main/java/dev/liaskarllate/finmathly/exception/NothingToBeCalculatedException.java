package dev.liaskarllate.finmathly.exception;

import java.io.Serial;

public class NothingToBeCalculatedException extends InputNotValidException {
    @Serial
    private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "There is nothing to be calculated. All values have been provided.";

	public NothingToBeCalculatedException() {
        super(MESSAGE);
    }
}
