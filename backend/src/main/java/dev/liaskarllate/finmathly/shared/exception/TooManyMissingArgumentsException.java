package dev.liaskarllate.finmathly.shared.exception;

import java.io.Serial;

import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

public class TooManyMissingArgumentsException extends InvalidInputException {
    private final int missingArgumentsCount;
    private final int expectedMissingArgumentsCount;
    private final int totalArgumentsCount;

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "The number of missing arguments, %d, is wrong: Only %d of %d must be missing.";

    public TooManyMissingArgumentsException(int missingArgumentsCount, int expectedMissingArgumentsCount,
            int totalArgumentsCount) {
        super(MESSAGE.formatted(missingArgumentsCount, expectedMissingArgumentsCount, totalArgumentsCount));

        this.missingArgumentsCount = missingArgumentsCount;
        this.expectedMissingArgumentsCount = expectedMissingArgumentsCount;
        this.totalArgumentsCount = totalArgumentsCount;
    }

    public int getMissingArgumentsCount() {
        return missingArgumentsCount;
    }

    public int getExpectedMissingArgumentsCount() {
        return expectedMissingArgumentsCount;
    }

    public int getTotalArgumentsCount() {
        return totalArgumentsCount;
    }
}
