package dev.liaskarllate.finmathly.interest.compound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.liaskarllate.finmathly.calculation.interest.compound.dto.CompoundInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.compound.dto.EquivalentInterestRateOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.compound.dto.FutureValueFormulaOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.compound.dto.PresentValueCashFlowInputDTO;
import dev.liaskarllate.finmathly.calculation.interest.shared.dto.FlowOutputDTO;
import dev.liaskarllate.finmathly.shared.controller.Helper;
import lombok.AllArgsConstructor;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
@Component
@AutoConfigureMockMvc
public class CompoundInterestControllerTester {
    private static final String BASE = "/calculations/interest/compound";

    private MockMvc mvc;
    private ObjectMapper json;

    public CompoundInterestFormulaOutputDTO applyInterestFormula(BigDecimal interest, BigDecimal presentValue,
            BigDecimal interestRate, BigDecimal time) throws Exception {

        final String path = "/interest";

        String queryParams = String.format(
                "?interest=%s" +
                        "&presentValue=%s" +
                        "&interestRate=%s" +
                        "&time=%s",
                Helper.toParam(interest),
                Helper.toParam(presentValue),
                Helper.toParam(interestRate),
                Helper.toParam(time));

        String responseBody = mvc.perform(get(BASE + path + queryParams))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return json.readValue(responseBody, CompoundInterestFormulaOutputDTO.class);
    }

    public FutureValueFormulaOutputDTO applyFutureValueFormula(BigDecimal futureValue, BigDecimal presentValue,
            BigDecimal interestRate, BigDecimal time) throws Exception {

        final String path = "/future-value";

        String queryParams = String.format(
                "?futureValue=%s" +
                        "&presentValue=%s" +
                        "&interestRate=%s" +
                        " &time=%s",
                Helper.toParam(futureValue),
                Helper.toParam(presentValue),
                Helper.toParam(interestRate),
                Helper.toParam(time));

        String responseBody = mvc.perform(get(BASE + path + queryParams))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return json.readValue(responseBody, FutureValueFormulaOutputDTO.class);
    }

    public FlowOutputDTO calculatePresentValueOfCashFlow(PresentValueCashFlowInputDTO input) throws Exception {
        final String path = "/present-value/cash-flow";

        String responseBody = mvc.perform(post(BASE + path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return json.readValue(responseBody, FlowOutputDTO.class);
    }

    public EquivalentInterestRateOutputDTO calculateEquivalentInterestRate(BigDecimal interestRate,
            String from, String to) throws Exception {

        final String path = "/equivalent-interest-rate";

        String queryParams = String.format(
                "?interestRate=%s" +
                        "&from=%s" +
                        "&to=%s",
                Helper.toParam(interestRate),
                from,
                to);

        String responseBody = mvc.perform(get(BASE + path + queryParams))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return json.readValue(responseBody, EquivalentInterestRateOutputDTO.class);
    }
}