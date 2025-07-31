package dev.liaskarllate.finmathly.shared.exception.global;

import java.io.Serial;

public class ResourceConflictException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
	
	public ResourceConflictException(String message) {
        super(message);
    }
}
