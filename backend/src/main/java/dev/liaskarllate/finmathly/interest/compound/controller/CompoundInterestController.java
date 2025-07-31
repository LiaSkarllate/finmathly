package dev.liaskarllate.finmathly.interest.compound.controller;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.interest.compound.dto.CompoundInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.compound.dto.EquivalentInterestRateOutputDTO;
import dev.liaskarllate.finmathly.interest.compound.dto.FutureValueFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.compound.dto.PresentValueCashFlowInputDTO;
import dev.liaskarllate.finmathly.interest.compound.mapper.CompoundInterestFormulaOutputMapper;
import dev.liaskarllate.finmathly.interest.compound.mapper.FutureValueFormulaOutputMapper;
import dev.liaskarllate.finmathly.interest.compound.mapper.PresentValueCashFlowInputMapper;
import dev.liaskarllate.finmathly.interest.compound.model.CompoundInterestFormulaOutput;
import dev.liaskarllate.finmathly.interest.compound.model.FutureValueFormulaOutput;
import dev.liaskarllate.finmathly.interest.compound.model.PresentValueCashFlowInput;
import dev.liaskarllate.finmathly.interest.compound.service.CompoundInterestFormulaService;
import dev.liaskarllate.finmathly.interest.compound.service.EquivalentCompoundInterestRateService;
import dev.liaskarllate.finmathly.interest.compound.service.FutureValueFormulaService;
import dev.liaskarllate.finmathly.interest.compound.service.PresentValueCashFlowService;
import dev.liaskarllate.finmathly.interest.shared.dto.FlowOutputDTO;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * REST Controller that exposes service through endpoints for performing
 * compound interest calculations.
 */
@AllArgsConstructor
@RestController
@Tag(name = "Compound interest calculations")
@RequestMapping("/calculations/interest/compound")
public class CompoundInterestController {
    private final CompoundInterestFormulaService compoundInterestFormulaService;
    private final FutureValueFormulaService futureValueFormulaService;
    private final PresentValueCashFlowService presentValueCashFlowService;
    private final EquivalentCompoundInterestRateService equivalentCompoundInterestRateService;

    private final CompoundInterestFormulaOutputMapper compoundInterestFormulaOutputMapper;
    private final FutureValueFormulaOutputMapper futureValueFormulaOutputMapper;
    private final PresentValueCashFlowInputMapper presentValueCashFlowInputMapper;

    /**
     * Applies the compound interest formula to calculate a missing parameter.
     *
     * Reference: Section 2.1 of the 12th edition of "Matemática Financeira e Suas
     * Aplicações" by Alexandre Assaf Neto.
     * 
     * @param interest     the interest (can be null if it needs to be calculated)
     * @param presentValue the present value (can be null if it needs to be
     *                     calculated)
     * @param interestRate the interest rate (can be null if it needs to be
     *                     calculated)
     * @param time         the time (can be null if it needs to be calculated)
     * @return a body containing all values, including the calculated one
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompoundInterestFormulaOutputDTO.class), examples = @ExampleObject(value = "{\"interest\": 21664.02, \"presentValue\": 88000.00, \"interestRate\": 0.045, \"time\": 5}")))
    })
    @GetMapping("/interest")
    public ResponseEntity<CompoundInterestFormulaOutputDTO> applyInterestFormula(
            @RequestParam(required = false) BigDecimal interest,
            @RequestParam(required = false) BigDecimal presentValue,
            @RequestParam(required = false) BigDecimal interestRate,
            @RequestParam(required = false) BigDecimal time) {

        CompoundInterestFormulaOutput compoundInterestFormulaOutput = this.compoundInterestFormulaService.applyFormula(
                interest,
                presentValue,
                interestRate,
                time);

        return ResponseEntity.ok(this.compoundInterestFormulaOutputMapper.toDTO(compoundInterestFormulaOutput));
    }

    /**
     * Applies the compound interest future value formula to calculate a missing
     * parameter.
     *
     * Reference: Section 2.1 of the 12th edition of "Matemática Financeira e Suas
     * Aplicações" by Alexandre Assaf Neto.
     * 
     * @param futureValue  the future value (can be null if it needs to be
     *                     calculated)
     * @param presentValue the present value (can be null if it needs to be
     *                     calculated)
     * @param interestRate the interest rate (can be null if it needs to be
     *                     calculated)
     * @param time         the time (can be null if it needs to be calculated)
     * @return a body containing all values, including the calculated one.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FutureValueFormulaOutputDTO.class), examples = @ExampleObject(value = "{\"futureValue\": 27500.00, \"presentValue\": 22463.70, \"interestRate\": 0.017, \"time\": 12}")))
    })
    @GetMapping({ "/future-value", "/present-value" })
    public ResponseEntity<FutureValueFormulaOutputDTO> applyFutureValueFormula(
            @RequestParam(required = false) BigDecimal futureValue,
            @RequestParam(required = false) BigDecimal presentValue,
            @RequestParam(required = false) BigDecimal interestRate,
            @RequestParam(required = false) BigDecimal time) {

        FutureValueFormulaOutput futureValueFormulaOutput = this.futureValueFormulaService.applyFormula(
                futureValue,
                presentValue,
                interestRate,
                time);

        return ResponseEntity.ok(this.futureValueFormulaOutputMapper.toDTO(futureValueFormulaOutput));
    }

    /**
     * Calculates the present value of a cash flow.
     *
     * Reference: Section 2.1.1 of the 12th edition of "Matemática Financeira e Suas
     * Aplicações" by Alexandre Assaf Neto.
     * 
     * @return The present value of the cash flow.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FlowOutputDTO.class), examples = @ExampleObject(value = "{\"value\": 145776.15, \"time\": 0}")))
    })
    @PostMapping("/present-value/cash-flow")
    public ResponseEntity<FlowOutputDTO> calculatePresentValueOfACashFlow(
            @Valid @RequestBody PresentValueCashFlowInputDTO presentValueCashFlowInputDTO) {

        PresentValueCashFlowInput presentValueCashFlowInput = this.presentValueCashFlowInputMapper
                .toModel(presentValueCashFlowInputDTO);

        BigDecimal presentValue = this.presentValueCashFlowService.calculatePresentValueOfACashFlow(
                presentValueCashFlowInput.getFlows(),
                presentValueCashFlowInput.getInterestRate());

        return ResponseEntity.ok(
                new FlowOutputDTO(
                        presentValue,
                        BigDecimal.valueOf(0)));
    }

    /**
     * Calculates the equivalent interest rate from the original to the target
     * capitalization period.
     *
     * Reference: Sections 2.2 and 2.3 of the 12th edition of "Matemática Financeira
     * e Suas Aplicações" by Alexandre Assaf Neto.
     * 
     * @param interestRate the interest rate for the original capitalization period
     * @param from         the original capitalization period
     * @param to           the target capitalization period
     * @return the equivalent interest rate for the target capitalization period
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquivalentInterestRateOutputDTO.class), examples = @ExampleObject(value = "{\"interestRate\": 0.0166, \"capitalizationPeriod\": \"MONTHLY\"}")))
    })
    @GetMapping({ "/equivalent-interest-rate", "/effective-interest-rate" })
    public ResponseEntity<EquivalentInterestRateOutputDTO> calculateEquivalentInterestRate(
            @RequestParam(required = true) BigDecimal interestRate,
            @RequestParam(required = true) CapitalizationPeriod from,
            @RequestParam(required = true) CapitalizationPeriod to) {

        BigDecimal equivalentInterestRate = equivalentCompoundInterestRateService.calculateEquivalentInterestRate(
                interestRate,
                from,
                to);

        return ResponseEntity.ok(
                new EquivalentInterestRateOutputDTO(
                        equivalentInterestRate,
                        to));
    }
}