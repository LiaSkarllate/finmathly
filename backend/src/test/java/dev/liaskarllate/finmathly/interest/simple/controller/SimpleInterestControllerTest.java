package dev.liaskarllate.finmathly.interest.simple.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import dev.liaskarllate.finmathly.calculation.interest.compound.dto.EquivalentInterestRateOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.shared.dto.FlowInputDTO;
import dev.liaskarllate.finmathly.calculation.interest.shared.dto.FlowOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.simple.dto.AmountFormulaOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.simple.dto.EquivalenceCashFlowInputDTO;
import dev.liaskarllate.finmathly.calculation.interest.simple.dto.SimpleInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SimpleInterestControllerTest {
    @Autowired
    private SimpleInterestControllerTester tester;

    @Test
    void shouldCalculateInterestUsingSimpleInterestFormula() throws Exception {
        final BigDecimal principal = new BigDecimal("80000.0");
        final BigDecimal interestRate = new BigDecimal("0.025");
        final BigDecimal time = new BigDecimal("3.0");
        final BigDecimal expectedInterest = new BigDecimal("6000.00");

        SimpleInterestFormulaOutputDTO simpleInterestFormulaOutputDTO = tester.applyInterestFormula(
                null, principal, interestRate, time);

        assertEquals(expectedInterest, simpleInterestFormulaOutputDTO.getInterest());
        assertEquals(principal, simpleInterestFormulaOutputDTO.getPrincipal());
        assertEquals(interestRate, simpleInterestFormulaOutputDTO.getInterestRate());
        assertEquals(time, simpleInterestFormulaOutputDTO.getTime());
    }

    @Test
    void shouldCalculateTimeUsingSimpleInterestFormula() throws Exception {
        final BigDecimal principal = new BigDecimal("50000.0");
        final BigDecimal interestRate = new BigDecimal("0.03");
        final BigDecimal interest = new BigDecimal("4500.0");
        final BigDecimal expectedTime = new BigDecimal("3.0");

        SimpleInterestFormulaOutputDTO simpleInterestFormulaOutputDTO = tester.applyInterestFormula(
                interest, principal, interestRate, null);

        assertEquals(interest, simpleInterestFormulaOutputDTO.getInterest());
        assertEquals(principal, simpleInterestFormulaOutputDTO.getPrincipal());
        assertEquals(interestRate, simpleInterestFormulaOutputDTO.getInterestRate());
        assertEquals(expectedTime, simpleInterestFormulaOutputDTO.getTime());
    }

    @Test
    void shouldCalculateAmountUsingSimpleAmountFormula() throws Exception {
        final BigDecimal principal = new BigDecimal("18000.0");
        final BigDecimal interestRate = new BigDecimal("0.015");
        final BigDecimal time = new BigDecimal("8.0");
        final BigDecimal expectedAmount = new BigDecimal("20160.00");

        AmountFormulaOutputDTO amountFormulaOutputDTO = tester.applyAmountFormula(
                null, principal, interestRate, time);

        assertEquals(expectedAmount, amountFormulaOutputDTO.getAmount());
        assertEquals(principal, amountFormulaOutputDTO.getPrincipal());
        assertEquals(interestRate, amountFormulaOutputDTO.getInterestRate());
        assertEquals(time, amountFormulaOutputDTO.getTime());
    }

    @Test
    void shouldCalculatePrincipalUsingSimpleAmountFormula() throws Exception {
        final BigDecimal amount = new BigDecimal("22000.0");
        final BigDecimal interestRate = new BigDecimal("0.02");
        final BigDecimal time = new BigDecimal("5.0");
        final BigDecimal expectedPrincipal = new BigDecimal("20000.00");

        AmountFormulaOutputDTO amountFormulaOutputDTO = tester.applyAmountFormula(
                amount, null, interestRate, time);

        assertEquals(amount, amountFormulaOutputDTO.getAmount());
        assertEquals(expectedPrincipal, amountFormulaOutputDTO.getPrincipal());
        assertEquals(interestRate, amountFormulaOutputDTO.getInterestRate());
        assertEquals(time, amountFormulaOutputDTO.getTime());
    }

    @Test
    void shouldCalculateRequiredTargetFlowValue() throws Exception {
        final var originalFlows = List.of(
                new FlowInputDTO(new BigDecimal("10000.0"), new BigDecimal("1.0")));
        final var proposedFlows = List.of(
                new FlowInputDTO(new BigDecimal("15000.0"), new BigDecimal("2.0")));

        final FlowInputDTO targetFlow = new FlowInputDTO(new BigDecimal("0.0"), new BigDecimal(0));
        final BigDecimal focalTime = new BigDecimal("2.0");
        final BigDecimal interestRate = new BigDecimal("0.05");
        final BigDecimal expectedValue = new BigDecimal("14285.71");

        var equivalenceCashFlowInputDTO = new EquivalenceCashFlowInputDTO(
                originalFlows, proposedFlows, targetFlow, focalTime, interestRate);

        FlowOutputDTO flowOutputDTO = tester.calculateRequiredTargetFlowValue(equivalenceCashFlowInputDTO);

        assertEquals(expectedValue, flowOutputDTO.getValue());
        assertEquals(focalTime, flowOutputDTO.getTime());
    }

    @Test
    void shouldCalculateEquivalentSimpleInterestRate() throws Exception {
        final BigDecimal interestRate = new BigDecimal("0.09");
        final CapitalizationPeriod from = CapitalizationPeriod.QUARTERLY;
        final CapitalizationPeriod to = CapitalizationPeriod.YEARLY;
        final BigDecimal expectedRate = new BigDecimal("0.0361");

        EquivalentInterestRateOutputDTO equivalentInterestRateOutputDTO = tester.calculateEquivalentInterestRate(
                interestRate, from, to);

        assertEquals(expectedRate, equivalentInterestRateOutputDTO.getInterestRate());
        assertEquals(to, equivalentInterestRateOutputDTO.getCapitalizationPeriod());
    }
}
