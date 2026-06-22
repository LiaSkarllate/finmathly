package dev.liaskarllate.finmathly.shared.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Rounding {
    private static final int MONEY_SCALE = 2;
    private static final int RATE_SCALE = 4;
    private static final int TIME_SCALE = 2;

    public static BigDecimal money(BigDecimal value) {
        return value.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal rate(BigDecimal value) {
        return value.setScale(RATE_SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal time(BigDecimal value) {
        return value.setScale(TIME_SCALE, RoundingMode.HALF_UP);
    }
}
