package dev.liaskarllate.finmathly.shared.exception;

import java.io.Serial;

import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;

public class RelatedResourceNotFoundException extends ResourceNotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;
	
	public RelatedResourceNotFoundException(String message) {
        super(message);
    }
}

