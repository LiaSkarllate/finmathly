package dev.liaskarllate.finmathly.shared.exception;

import java.io.Serial;

import dev.liaskarllate.finmathly.shared.exception.global.InvalidInputException;

public class InvalidMissingParametersCountException extends InvalidInputException {
    private final int missingParametersCount;
    private final int expectedMissingParametersCount;
    private final int totalParametersCount;

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "The number of missing parameters, %d, is wrong: %d of %d must be missing.";

    public InvalidMissingParametersCountException(int missingParametersCount, int expectedMissingParametersCount,
            int totalParametersCount) {
        super(MESSAGE.formatted(missingParametersCount, expectedMissingParametersCount, totalParametersCount));

        this.missingParametersCount = missingParametersCount;
        this.expectedMissingParametersCount = expectedMissingParametersCount;
        this.totalParametersCount = totalParametersCount;
    }

    public int getMissingParametersCount() {
        return missingParametersCount;
    }

    public int getExpectedMissingParametersCount() {
        return expectedMissingParametersCount;
    }

    public int getTotalParametersCount() {
        return totalParametersCount;
    }
}
