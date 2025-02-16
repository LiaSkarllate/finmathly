package dev.liaskarllate.finmathly.exception;

public class InputNotValidException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InputNotValidException(String message) {
        super(message);
    }
}
