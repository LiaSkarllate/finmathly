package dev.liaskarllate.finmathly.exception;

public abstract class ExceptionThatShouldBeImpossible extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ExceptionThatShouldBeImpossible(String message) {
        super(message);
    }
}
