package dev.liaskarllate.finmathly.interest.compound.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import dev.liaskarllate.finmathly.interest.compound.dto.CompoundInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.compound.dto.FutureValueFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.compound.dto.PresentValueCashFlowInputDTO;
import dev.liaskarllate.finmathly.interest.shared.dto.FlowInputDTO;
import dev.liaskarllate.finmathly.interest.compound.dto.EquivalentInterestRateOutputDTO;
import dev.liaskarllate.finmathly.interest.shared.dto.FlowOutputDTO;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc
class CompoundInterestControllerTest {
    private CompoundInterestControllerTester tester;

    @Test
    void shouldCalculateInterestUsingInterestFormula() throws Exception {
        final BigDecimal presentValue = new BigDecimal("88000.0");
        final BigDecimal interestRate = new BigDecimal("0.045");
        final BigDecimal time = new BigDecimal("5.0");
        final BigDecimal expectedInterest = new BigDecimal("21664.02");

        CompoundInterestFormulaOutputDTO compoundInterestFormulaOutputDTO = tester.applyInterestFormula(
                null, presentValue, interestRate, time);

        assertEquals(expectedInterest, compoundInterestFormulaOutputDTO.getInterest());
        assertEquals(presentValue, compoundInterestFormulaOutputDTO.getPresentValue());
        assertEquals(interestRate, compoundInterestFormulaOutputDTO.getInterestRate());
        assertEquals(time, compoundInterestFormulaOutputDTO.getTime());
    }

    @Test
    void shouldCalculateTimeUsingInterestFormula() throws Exception {
        final BigDecimal presentValue = new BigDecimal("1000.0");
        final BigDecimal interestRate = new BigDecimal("0.08");
        final BigDecimal interest = new BigDecimal("500.0");
        final BigDecimal expectedTime = new BigDecimal("5.27");

        CompoundInterestFormulaOutputDTO compoundInterestFormulaOutputDTO = tester.applyInterestFormula(
                interest, presentValue, interestRate, null);

        assertEquals(interest, compoundInterestFormulaOutputDTO.getInterest());
        assertEquals(presentValue, compoundInterestFormulaOutputDTO.getPresentValue());
        assertEquals(interestRate, compoundInterestFormulaOutputDTO.getInterestRate());
        assertEquals(expectedTime, compoundInterestFormulaOutputDTO.getTime());
    }

    @Test
    void shouldCalculateFutureValueUsingFutureValueFormula() throws Exception {
        final BigDecimal presentValue = new BigDecimal("12000.0");
        final BigDecimal interestRate = new BigDecimal("0.035");
        final BigDecimal time = new BigDecimal("8.0");
        final BigDecimal expectedFutureValue = new BigDecimal("15801.71");

        FutureValueFormulaOutputDTO futureValueFormulaOutputDTO = tester.applyFutureValueFormula(
                null, presentValue, interestRate, time);

        assertEquals(expectedFutureValue, futureValueFormulaOutputDTO.getFutureValue());
        assertEquals(presentValue, futureValueFormulaOutputDTO.getPresentValue());
        assertEquals(interestRate, futureValueFormulaOutputDTO.getInterestRate());
        assertEquals(time, futureValueFormulaOutputDTO.getTime());
    }

    @Test
    void shouldCalculatePresentValueFromFlows() throws Exception {
        var flows = List.of(
                new FlowInputDTO(new BigDecimal("15000.0"), new BigDecimal("2.0")),
                new FlowInputDTO(new BigDecimal("40000.0"), new BigDecimal("5.0")),
                new FlowInputDTO(new BigDecimal("50000.0"), new BigDecimal("6.0")),
                new FlowInputDTO(new BigDecimal("70000.0"), new BigDecimal("8.0")));

        BigDecimal interestRate = new BigDecimal("0.03");
        BigDecimal expectedPresentValue = new BigDecimal("145776.15");

        var presentValueCashFlowInputDTO = new PresentValueCashFlowInputDTO(flows, interestRate);

        FlowOutputDTO result = tester.calculatePresentValueOfCashFlow(presentValueCashFlowInputDTO);

        assertEquals(expectedPresentValue, result.getValue());
        assertEquals(BigDecimal.ZERO, result.getTime());
    }

    @Test
    void shouldCalculateEquivalentCompoundInterestRateForSamePeriod() throws Exception {
        BigDecimal interestRate = new BigDecimal("0.25");
        String from = CapitalizationPeriod.YEARLY.getDescription();
        String to = CapitalizationPeriod.MONTHLY.getDescription();
        BigDecimal expectedRate = new BigDecimal("0.01877");

        EquivalentInterestRateOutputDTO result = tester.calculateEquivalentInterestRate(
                interestRate, from, to);

        assertEquals(expectedRate, result.getInterestRate());
        assertEquals(to, result.getCapitalizationPeriod().getDescription());
    }
}
