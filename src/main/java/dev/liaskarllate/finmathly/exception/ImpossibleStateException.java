package dev.liaskarllate.finmathly.exception;

public class ImpossibleStateException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ImpossibleStateException(String message) {
        super(message);
    }
}
