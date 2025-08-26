package dev.liaskarllate.finmathly.interest.simple.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.interest.compound.dto.EquivalentInterestRateOutputDTO;
import dev.liaskarllate.finmathly.interest.shared.dto.FlowOutputDTO;
import dev.liaskarllate.finmathly.interest.simple.dto.AmountFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.simple.dto.EquivalenceCashFlowInputDTO;
import dev.liaskarllate.finmathly.interest.simple.dto.SimpleInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.simple.mapper.AmountFormulaOutputMapper;
import dev.liaskarllate.finmathly.interest.simple.mapper.EquivalenceCashFlowInputMapper;
import dev.liaskarllate.finmathly.interest.simple.mapper.SimpleInterestFormulaOutputMapper;
import dev.liaskarllate.finmathly.interest.simple.model.AmountFormulaOutput;
import dev.liaskarllate.finmathly.interest.simple.model.EquivalenceCashFlowInput;
import dev.liaskarllate.finmathly.interest.simple.model.SimpleInterestFormulaOutput;
import dev.liaskarllate.finmathly.interest.simple.service.AmountFormulaService;
import dev.liaskarllate.finmathly.interest.simple.service.EquivalentCashFlowService;
import dev.liaskarllate.finmathly.interest.simple.service.EquivalentSimpleInterestRateService;
import dev.liaskarllate.finmathly.interest.simple.service.SimpleInterestFormulaService;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * REST Controller that exposes service through endpoints for performing simple
 * interest calculations.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/calculations/interest/simple")
@Tag(name = "Simple interest calculations")
public class SimpleInterestController {
    private final SimpleInterestFormulaService simpleInterestFormulaService;
    private final AmountFormulaService amountFormulaService;
    private final EquivalentCashFlowService equivalentCashFlowService;
    private final EquivalentSimpleInterestRateService equivalentSimpleInterestRateService;

    private final SimpleInterestFormulaOutputMapper simpleInterestFormulaOutputMapper;
    private final AmountFormulaOutputMapper amountFormulaOutputMapper;
    private final EquivalenceCashFlowInputMapper equivalenceCashFlowInputMapper;

    /**
     * Applies the simple interest formula to calculate a missing parameter.
     * 
     * Reference: Section 1.8 of the 12th edition of "Matemática Financeira e Suas
     * Aplicações" by Alexandre Assaf Neto.
     *
     * @param interest     the interest (can be null if it needs to be calculated)
     * @param principal    the principal (can be null if it needs to be calculated)
     * @param interestRate the interest rate (can be null if it needs to be
     *                     calculated)
     * @param time         the time (can be null if it needs to be calculated)
     * @return a body containing all values, including the calculated one
     */
    @Operation(summary = "Apply interest formula", tags = { "Simple interest calculations" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the calculated value.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimpleInterestFormulaOutputDTO.class)))
    })
    @GetMapping("/interest")
    public ResponseEntity<SimpleInterestFormulaOutputDTO> applyInterestFormula(
            @RequestParam(required = false) BigDecimal interest,
            @RequestParam(required = false) BigDecimal principal,
            @RequestParam(required = false) BigDecimal interestRate,
            @RequestParam(required = false) BigDecimal time) {

        SimpleInterestFormulaOutput simpleInterestFormulaOutput = this.simpleInterestFormulaService.applyFormula(
                interest,
                principal,
                interestRate,
                time);

        return ResponseEntity.ok(this.simpleInterestFormulaOutputMapper.toDTO(simpleInterestFormulaOutput));
    }

    /**
     * Applies the simple interest amount formula to calculate a missing parameter.
     *
     * Reference: Section 1.9 of the 12th edition of "Matemática Financeira e Suas
     * Aplicações" by Alexandre Assaf Neto.
     * 
     * @param amount       the amount (can be null if it needs to be calculated)
     * @param principal    the principal amount (can be null if it needs to be
     *                     calculated)
     * @param interestRate the interest rate (can be null if it needs to be
     *                     calculated)
     * @param time         the time (can be null if it needs to be calculated)
     * @return a body containing all values, including the calculated one
     */
    @Operation(summary = "Apply amount formula", tags = { "Simple interest calculations" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the calculated value.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AmountFormulaOutputDTO.class)))
    })
    @GetMapping({ "/amount", "/principal" })
    public ResponseEntity<AmountFormulaOutputDTO> applyAmountFormula(
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) BigDecimal principal,
            @RequestParam(required = false) BigDecimal interestRate,
            @RequestParam(required = false) BigDecimal time) {

        AmountFormulaOutput amountFormulaOutput = this.amountFormulaService.applyFormula(
                amount,
                principal,
                interestRate,
                time);

        return ResponseEntity.ok(this.amountFormulaOutputMapper.toDTO(amountFormulaOutput));
    }

    /**
     * Calculates the required value of a target flow from a proposed cash flow to
     * balance
     * an original one, ensuring equivalence.
     * 
     * Reference: Section 1.12 of the 12th edition of "Matemática Financeira e Suas
     * Aplicações" by Alexandre Assaf Neto.
     *
     * @return the target flow with the required value to ensure equivalence
     */
    @Operation(summary = "Calculate required target flow value", tags = { "Simple interest calculations" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the calculated required target flow value.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FlowOutputDTO.class)))
    })
    @PostMapping("/equivalence-cash-flow")
    public ResponseEntity<FlowOutputDTO> calculateRequiredTargetFlowValue(
            @RequestBody @Valid EquivalenceCashFlowInputDTO equivalenceCashFlowInputDTO) {

        EquivalenceCashFlowInput equivalenceCashFlowInput = this.equivalenceCashFlowInputMapper
                .toModel(equivalenceCashFlowInputDTO);

        BigDecimal targetValue = this.equivalentCashFlowService.calculateRequiredTargetFlowValue(
                equivalenceCashFlowInput.getOriginalFlows(),
                equivalenceCashFlowInput.getProposedFlows(),
                equivalenceCashFlowInput.getTargetFlow(),
                equivalenceCashFlowInput.getFocalTime(),
                equivalenceCashFlowInput.getInterestRate());

        return ResponseEntity.ok(
                new FlowOutputDTO(
                        targetValue,
                        equivalenceCashFlowInputDTO.getFocalTime()));
    }

    /**
     * Calculates the equivalent interest rate from an original to a target
     * capitalization period.
     *
     * Reference: Section 2.2 of the 12th edition of "Matemática Financeira e Suas
     * Aplicações" by Alexandre Assaf Neto.
     * 
     * @param interestRate the interest rate for the original capitalization period
     * @param from         the original capitalization period
     * @param to           the target capitalization period
     * @return the equivalent interest rate for the target capitalization period
     */
    @Operation(summary = "Calculate equivalent interest rate", tags = { "Simple interest calculations" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the calculated equivalent interest rate.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquivalentInterestRateOutputDTO.class)))
    })
    @GetMapping("/equivalent-interest-rate")
    public ResponseEntity<EquivalentInterestRateOutputDTO> calculateEquivalentInterestRate(
            @RequestParam(required = true) BigDecimal interestRate,
            @RequestParam(required = true) CapitalizationPeriod from,
            @RequestParam(required = true) CapitalizationPeriod to) {

        BigDecimal equivalentInterestRate = equivalentSimpleInterestRateService.calculateEquivalentInterestRate(
                interestRate,
                from,
                to);

        return ResponseEntity.ok(
                new EquivalentInterestRateOutputDTO(
                        equivalentInterestRate,
                        to));
    }
}