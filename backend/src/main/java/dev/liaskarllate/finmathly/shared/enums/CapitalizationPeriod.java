package dev.liaskarllate.finmathly.shared.enums;

import java.math.BigDecimal;

/**
 * Enum representing interest rate periods and their conversion factors.
 */
public enum CapitalizationPeriod {
    MONTHLY(BigDecimal.ONE, "monthly"),
    YEARLY(new BigDecimal(12), "yearly"),
    QUARTERLY(new BigDecimal(3), "quarterly"),
    FOUR_MONTHLY(new BigDecimal(4), "four-monthly"),
    HALF_YEARLY(new BigDecimal(6), "half-Yearly"),
    DAILY_360(BigDecimal.ONE.divide(new BigDecimal("30")), "daily (360-day year)"),
    DAILY_252(BigDecimal.ONE.divide(new BigDecimal("21")), "daily (252-day year)");

    private final BigDecimal factor;
    private final String description;

    CapitalizationPeriod(BigDecimal factor, String description) {
        this.factor = factor;
        this.description = description;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public String getDescription() {
        return description;
    }
}