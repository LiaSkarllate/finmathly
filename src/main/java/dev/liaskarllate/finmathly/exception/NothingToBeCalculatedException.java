package dev.liaskarllate.finmathly.exception;

public class NothingToBeCalculatedException extends ExceptionThatShouldBeImpossible{
	private static final long serialVersionUID = 1L;
	private static final String message = "There is nothing to be calculated: All parameters have been provided.";

	public NothingToBeCalculatedException() {
        super(message);
    }
}
