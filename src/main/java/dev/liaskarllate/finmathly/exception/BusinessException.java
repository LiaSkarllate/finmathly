package dev.liaskarllate.finmathly.exception;

import java.io.Serial;

public abstract class BusinessException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The operation was aborted. ";

	protected BusinessException(String datails) {
        super(MESSAGE + datails);
    }
}
