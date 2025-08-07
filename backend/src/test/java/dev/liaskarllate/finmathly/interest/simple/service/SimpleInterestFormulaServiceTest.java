package dev.liaskarllate.finmathly.interest.simple.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.interest.simple.model.SimpleInterestFormulaOutput;
import dev.liaskarllate.finmathly.shared.exception.NothingToBeCalculatedException;
import dev.liaskarllate.finmathly.shared.exception.InvalidMissingParametersCountException;

class SimpleInterestFormulaServiceTest {

    private SimpleInterestFormulaService simpleInterestFormulaService;

    @BeforeEach
    void setUp() {
        simpleInterestFormulaService = new SimpleInterestFormulaService();
    }

    @Test
    void shouldCalculateInterestWhenInterestIsNull() {
        BigDecimal principal = new BigDecimal("100");
        BigDecimal interestRate = new BigDecimal("0.05");
        BigDecimal time = new BigDecimal("2");

        SimpleInterestFormulaOutput output = simpleInterestFormulaService.applyFormula(null, principal, interestRate,
                time);

        assertThat(output.getInterest()).isEqualByComparingTo(new BigDecimal("10"));
        assertThat(output.getPrincipal()).isEqualByComparingTo(principal);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculatePrincipalWhenPrincipalIsNull() {
        BigDecimal interest = new BigDecimal("10");
        BigDecimal interestRate = new BigDecimal("0.05");
        BigDecimal time = new BigDecimal("2");

        SimpleInterestFormulaOutput output = simpleInterestFormulaService.applyFormula(interest, null, interestRate,
                time);

        assertThat(output.getPrincipal()).isEqualByComparingTo(new BigDecimal("100"));
        assertThat(output.getInterest()).isEqualByComparingTo(interest);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculateInterestRateWhenInterestRateIsNull() {
        BigDecimal interest = new BigDecimal("10");
        BigDecimal principal = new BigDecimal("100");
        BigDecimal time = new BigDecimal("2");

        SimpleInterestFormulaOutput output = simpleInterestFormulaService.applyFormula(interest, principal, null, time);

        assertThat(output.getInterestRate()).isEqualByComparingTo(new BigDecimal("0.05"));
        assertThat(output.getInterest()).isEqualByComparingTo(interest);
        assertThat(output.getPrincipal()).isEqualByComparingTo(principal);
        assertThat(output.getTime()).isEqualByComparingTo(time);
    }

    @Test
    void shouldCalculateTimeWhenTimeIsNull() {
        BigDecimal interest = new BigDecimal("10");
        BigDecimal principal = new BigDecimal("100");
        BigDecimal interestRate = new BigDecimal("0.05");

        SimpleInterestFormulaOutput output = simpleInterestFormulaService.applyFormula(interest, principal,
                interestRate, null);

        assertThat(output.getTime()).isEqualByComparingTo(new BigDecimal("2"));
        assertThat(output.getInterest()).isEqualByComparingTo(interest);
        assertThat(output.getPrincipal()).isEqualByComparingTo(principal);
        assertThat(output.getInterestRate()).isEqualByComparingTo(interestRate);
    }

    @Test
    void shouldThrowWhenNoParameterIsNull() {
        BigDecimal interest = new BigDecimal("10");
        BigDecimal principal = new BigDecimal("100");
        BigDecimal interestRate = new BigDecimal("0.05");
        BigDecimal time = new BigDecimal("2");

        assertThatThrownBy(() -> simpleInterestFormulaService.applyFormula(interest, principal, interestRate, time))
                .isInstanceOf(NothingToBeCalculatedException.class);
    }

    @Test
    void shouldThrowWhenMoreThanOneParameterIsNull() {
        BigDecimal interestRate = new BigDecimal("0.05");
        BigDecimal time = new BigDecimal("2");

        assertThatThrownBy(() -> simpleInterestFormulaService.applyFormula(null, null, interestRate, time))
                .isInstanceOfSatisfying(InvalidMissingParametersCountException.class, e -> {

                    final int missingParametersCount = 2;
                    final int expectedMissingParametersCount = 1;
                    final int totalParametersCount = 4;

                    assertThat(e.getMissingParametersCount()).isEqualTo(missingParametersCount);
                    assertThat(e.getExpectedMissingParametersCount()).isEqualTo(expectedMissingParametersCount);
                    assertThat(e.getTotalParametersCount()).isEqualTo(totalParametersCount);
                });
    }
}