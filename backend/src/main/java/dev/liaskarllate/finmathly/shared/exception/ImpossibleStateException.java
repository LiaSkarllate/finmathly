package dev.liaskarllate.finmathly.shared.exception;

import java.io.Serial;

import dev.liaskarllate.finmathly.shared.exception.global.BusinessRuleViolationException;

public class ImpossibleStateException extends BusinessRuleViolationException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "The system got into an impossible state. Please, contact the support team.";
	
	public ImpossibleStateException() {
        super(MESSAGE);
    }
}
