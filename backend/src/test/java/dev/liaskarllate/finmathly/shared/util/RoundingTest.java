package dev.liaskarllate.finmathly.shared.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class RoundingTest {
    @Test
    void shouldRoundMoneyToTwoDecimalPlacesHalfUp() {
        assertThat(Rounding.money(new BigDecimal("100.005"))).isEqualTo(new BigDecimal("100.01"));
        assertThat(Rounding.money(new BigDecimal("100.004"))).isEqualTo(new BigDecimal("100.00"));
        assertThat(Rounding.money(new BigDecimal("100"))).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    void shouldRoundRateToFourDecimalPlacesHalfUp() {
        assertThat(Rounding.rate(new BigDecimal("0.018769265121506"))).isEqualTo(new BigDecimal("0.0188"));
        assertThat(Rounding.rate(new BigDecimal("0.03"))).isEqualTo(new BigDecimal("0.0300"));
    }

    @Test
    void shouldRoundTimeToTwoDecimalPlacesHalfUp() {
        assertThat(Rounding.time(new BigDecimal("3"))).isEqualTo(new BigDecimal("3.00"));
        assertThat(Rounding.time(new BigDecimal("5.275"))).isEqualTo(new BigDecimal("5.28"));
    }
}
