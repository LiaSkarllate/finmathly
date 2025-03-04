package dev.liaskarllate.finmathly.exception;

import java.io.Serial;

public class InputNotValidException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
	
	public InputNotValidException(String message) {
        super(message);
    }
}
