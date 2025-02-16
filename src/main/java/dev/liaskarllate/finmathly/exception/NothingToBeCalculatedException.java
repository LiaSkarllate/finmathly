package dev.liaskarllate.finmathly.exception;

public class NothingToBeCalculatedException extends InputNotValidException {
	private static final long serialVersionUID = 1L;
	private static final String message = "There is nothing to be calculated. All values have been provided.";

	public NothingToBeCalculatedException() {
        super(message);
    }
}
