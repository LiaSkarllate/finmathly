package dev.liaskarllate.finmathly.shared.exception;

import java.io.Serial;

import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

public class ForbiddenArgumentException extends InvalidInputException {
    @Serial
    private static final long serialVersionUID = 1L;
	
	public ForbiddenArgumentException(String message) {
        super(message);
    }
}