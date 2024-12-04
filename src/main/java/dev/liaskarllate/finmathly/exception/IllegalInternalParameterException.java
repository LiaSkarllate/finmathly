package dev.liaskarllate.finmathly.exception;

public class IllegalInternalParameterException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private static final String message = "A internal condition has not been fulfilled.";
	
	public IllegalInternalParameterException() {
        super(message);
    }
}
