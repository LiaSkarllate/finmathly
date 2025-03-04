package dev.liaskarllate.finmathly.exception;

import java.io.Serial;

public class ImpossibleStateException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
	
	public ImpossibleStateException(String message) {
        super(message);
    }
}
