package dev.liaskarllate.finmathly.model.interest.compound;

/**
 * Enum representing interest rate periods and their conversion factors.
 */
public enum CapitalizationPeriod {
    MONTHLY(1d, "monthly"),
    YEARLY(12d, "yearly"),
    QUARTERLY(3d, "quarterly"),
    FOUR_MONTHLY(4d, "four-monthly"),
    HALF_YEARLY(6d, "half-Yearly"),
    DAILY_360(1/30d, "daily (360-day year)"),
    DAILY_252(1/21d, "daily (252-day year)");

    private final Double factor;
    private final String description;

    CapitalizationPeriod(Double factor, String description) {
        this.factor = factor;
        this.description = description;
    }

    public Double getFactor() {
        return factor;
    }

    public String getDescription() {
        return description;
    }
}