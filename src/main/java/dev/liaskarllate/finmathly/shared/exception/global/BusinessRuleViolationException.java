package dev.liaskarllate.finmathly.shared.exception.global;

import java.io.Serial;

public abstract class BusinessRuleViolationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The operation was aborted. ";

	protected BusinessRuleViolationException(String datails) {
        super(MESSAGE + datails);
    }
}
