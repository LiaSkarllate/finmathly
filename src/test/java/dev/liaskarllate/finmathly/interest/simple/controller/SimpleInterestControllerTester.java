package dev.liaskarllate.finmathly.interest.simple.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.liaskarllate.finmathly.interest.simple.dto.SimpleInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.shared.controller.Helper;
import dev.liaskarllate.finmathly.interest.simple.dto.AmountFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.simple.dto.EquivalenceCashFlowInputDTO;
import dev.liaskarllate.finmathly.interest.shared.dto.FlowOutputDTO;
import dev.liaskarllate.finmathly.interest.compound.dto.EquivalentInterestRateOutputDTO;
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
public class SimpleInterestControllerTester {
    private static final String BASE = "/calculations/interest/simple";

    private MockMvc mvc;
    private ObjectMapper json;

    public SimpleInterestFormulaOutputDTO applyInterestFormula(
            BigDecimal interest,
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) throws Exception {

        String path = "/interest";
        String queryParams = String.format(
                "?interest=%s" +
                        "&principal=%s" +
                        "&interestRate=%s" +
                        "&time=%s",
                Helper.toParam(interest),
                Helper.toParam(principal),
                Helper.toParam(interestRate),
                Helper.toParam(time));

        String responseBody = mvc.perform(get(BASE + path + queryParams))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return json.readValue(responseBody, SimpleInterestFormulaOutputDTO.class);
    }

    public AmountFormulaOutputDTO applyAmountFormula(
            BigDecimal amount,
            BigDecimal principal,
            BigDecimal interestRate,
            BigDecimal time) throws Exception {

        final String path = "/amount";

        String queryParams = String.format(
                "?amount=%s" +
                        "&principal=%s" +
                        "&interestRate=%s" +
                        "&time=%s",
                Helper.toParam(amount),
                Helper.toParam(principal),
                Helper.toParam(interestRate),
                Helper.toParam(time));

        String responseBody = mvc.perform(get(BASE + path + queryParams))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return json.readValue(responseBody, AmountFormulaOutputDTO.class);
    }

    public FlowOutputDTO calculateRequiredTargetFlowValue(
            EquivalenceCashFlowInputDTO input) throws Exception {
        final String path = "/equivalence-cash-flow";

        String responseBody = mvc.perform(post(BASE + path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return json.readValue(responseBody, FlowOutputDTO.class);
    }

    public EquivalentInterestRateOutputDTO calculateEquivalentInterestRate(
            BigDecimal interestRate,
            String from,
            String to) throws Exception {

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
