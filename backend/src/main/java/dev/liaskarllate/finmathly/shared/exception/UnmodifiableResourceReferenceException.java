package dev.liaskarllate.finmathly.shared.exception;

import java.io.Serial;

import dev.liaskarllate.finmathly.shared.exception.global.ResourceConflictException;

public class UnmodifiableResourceReferenceException extends ResourceConflictException {
    @Serial
    private static final long serialVersionUID = 1L;
	
	public UnmodifiableResourceReferenceException(String message) {
        super(message);
    }
}
