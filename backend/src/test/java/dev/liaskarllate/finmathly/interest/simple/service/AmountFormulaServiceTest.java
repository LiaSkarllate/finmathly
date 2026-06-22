package dev.liaskarllate.finmathly.interest.simple.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.calculation.interest.simple.model.AmountFormulaOutput;
import dev.liaskarllate.finmathly.calculation.interest.simple.service.AmountFormulaService;
import dev.liaskarllate.finmathly.shared.exception.InvalidMissingParametersCountException;
import dev.liaskarllate.finmathly.shared.exception.NothingToBeCalculatedException;

class AmountFormulaServiceTest {
    private AmountFormulaService amountFormulaService;

    @BeforeEach
    void setUp() {
        amountFormulaService = new AmountFormulaService();
    }

    @Test
    void shouldCalculateAmountWhenAmountIsNull() {
        BigDecimal principal = new BigDecimal("18000.0");
        BigDecimal interestRate = new BigDecimal("0.015");
        BigDecimal time = new BigDecimal("8.0");

        AmountFormulaOutput output = amountFormulaService.applyFormula(null, principal, interestRate, time);

        assertThat(output.getAmount()).isEqualByComparingTo(new BigDecimal("20160.00"));
        assertThat(output.getAmount().scale()).isEqualTo(2);
        assertThat(output.getPrincipal()).isEqualByComparingTo(principal);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculatePrincipalWhenPrincipalIsNull() {
        BigDecimal amount = new BigDecimal("22000.0");
        BigDecimal interestRate = new BigDecimal("0.02");
        BigDecimal time = new BigDecimal("5.0");

        AmountFormulaOutput output = amountFormulaService.applyFormula(amount, null, interestRate, time);

        assertThat(output.getPrincipal()).isEqualByComparingTo(new BigDecimal("20000.00"));
        assertThat(output.getAmount()).isEqualByComparingTo(amount);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculateInterestRateWhenInterestRateIsNull() {
        BigDecimal amount = new BigDecimal("22000.0");
        BigDecimal principal = new BigDecimal("20000.0");
        BigDecimal time = new BigDecimal("5.0");

        AmountFormulaOutput output = amountFormulaService.applyFormula(amount, principal, null, time);

        assertThat(output.getInterestRate()).isEqualByComparingTo(new BigDecimal("0.02"));
        assertThat(output.getInterestRate().scale()).isEqualTo(4);
        assertThat(output.getAmount()).isEqualByComparingTo(amount);
        assertThat(output.getPrincipal()).isEqualByComparingTo(principal);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculateTimeWhenTimeIsNull() {
        BigDecimal amount = new BigDecimal("22000.0");
        BigDecimal principal = new BigDecimal("20000.0");
        BigDecimal interestRate = new BigDecimal("0.02");

        AmountFormulaOutput output = amountFormulaService.applyFormula(amount, principal, interestRate, null);

        assertThat(output.getTime()).isEqualByComparingTo(new BigDecimal("5.00"));
        assertThat(output.getAmount()).isEqualByComparingTo(amount);
        assertThat(output.getPrincipal()).isEqualByComparingTo(principal);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
    }

    @Test
    void shouldThrowWhenNoParameterIsNull() {
        BigDecimal amount = new BigDecimal("22000.0");
        BigDecimal principal = new BigDecimal("20000.0");
        BigDecimal interestRate = new BigDecimal("0.02");
        BigDecimal time = new BigDecimal("5.0");

        assertThatThrownBy(() -> amountFormulaService.applyFormula(amount, principal, interestRate, time))
                .isInstanceOf(InvalidMissingParametersCountException.class);
    }

    @Test
    void shouldThrowWhenMoreThanOneParameterIsNull() {
        BigDecimal interestRate = new BigDecimal("0.02");
        BigDecimal time = new BigDecimal("5.0");

        assertThatThrownBy(() -> amountFormulaService.applyFormula(null, null, interestRate, time))
                .isInstanceOfSatisfying(InvalidMissingParametersCountException.class, e -> {
                    assertThat(e.getMissingParametersCount()).isEqualTo(2);
                    assertThat(e.getExpectedMissingParametersCount()).isEqualTo(1);
                    assertThat(e.getTotalParametersCount()).isEqualTo(4);
                });
    }
}
