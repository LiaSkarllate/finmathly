package dev.liaskarllate.finmathly.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.dto.FlowOutputDTO;
import dev.liaskarllate.finmathly.dto.ObjectFactoryDTO;
import dev.liaskarllate.finmathly.dto.interest.EquivalentInterestRateOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.compound.CompoundInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.compound.FutureValueFormulaOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.compound.PresentValueCashFlowInputDTO;
import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;
import dev.liaskarllate.finmathly.model.interest.compound.CompoundInterestFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.FutureValueFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.PresentValueCashFlowInput;
import dev.liaskarllate.finmathly.service.interest.compound.EquivalentCompoundInterestRateService;
import dev.liaskarllate.finmathly.service.interest.compound.CompoundInterestFormulaService;
import dev.liaskarllate.finmathly.service.interest.compound.FutureValueFormulaService;
import dev.liaskarllate.finmathly.service.interest.compound.PresentValueCashFlowService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST Controller that exposes service through endpoints for performing compound interest calculations.
 */
@RestController
@Tag(name = "Compound interest calculations")
@RequestMapping("/calculations/interest/compound")
public class CompoundInterestController {
    @Autowired
    private CompoundInterestFormulaService compoundInterestFormulaService;
    
    @Autowired
    private FutureValueFormulaService futureValueFormulaService;
    
    @Autowired
    private PresentValueCashFlowService presentValueCashFlowService;
    
    @Autowired
    private EquivalentCompoundInterestRateService equivalentCompoundInterestRateService;
    
    /**
     * Applies the compound interest formula to calculate a missing parameter.
     *
     * Reference: Section 2.1 of the 12th edition of "Matemática Financeira e Suas Aplicações" by Alexandre Assaf Neto.
     * 
     * @param interest      the interest (can be null if it needs to be calculated)
     * @param presentValue  the present value (can be null if it needs to be calculated)
     * @param interestRate  the interest rate (can be null if it needs to be calculated)
     * @param time          the time (can be null if it needs to be calculated)
     * @return a body containing all values, including the calculated one
     */
    @ApiResponses(value = {
        @ApiResponse(
		responseCode = "200", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = CompoundInterestFormulaOutputDTO.class),
            examples = @ExampleObject(
                value = "{\"interest\": 21664.02, \"presentValue\": 88000.00, \"interestRate\": 0.045, \"time\": 5}")))
    })
    @GetMapping("/interest")
    public ResponseEntity<CompoundInterestFormulaOutputDTO> applyInterestFormula(
    		@RequestParam(required = false) Double interest,
    		@RequestParam(required = false) Double presentValue, 
    		@RequestParam(required = false) Double interestRate, 
    		@RequestParam(required = false) Double time) {
    	
		CompoundInterestFormulaOutput compoundInterestFormulaOutput 
            = this.compoundInterestFormulaService.applyFormula(
				interest,
				presentValue,
				interestRate,
				time);
		
        return ResponseEntity.ok(compoundInterestFormulaOutput.toDTO());
    }
    
    /**
     * Applies the compound interest future value formula to calculate a missing parameter.
     *
     * Reference: Section 2.1 of the 12th edition of "Matemática Financeira e Suas Aplicações" by Alexandre Assaf Neto.
     * 
     * @param futureValue   the future value (can be null if it needs to be calculated)
     * @param presentValue  the present value (can be null if it needs to be calculated)
     * @param interestRate  the interest rate (can be null if it needs to be calculated)
     * @param time          the time (can be null if it needs to be calculated)
     * @return a body containing all values, including the calculated one.
     */
    @ApiResponses(value = {
        @ApiResponse(
		responseCode = "200", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = FutureValueFormulaOutputDTO.class),
            examples = @ExampleObject(
                value = "{\"futureValue\": 27500.00, \"presentValue\": 22463.70, \"interestRate\": 0.017, \"time\": 12}")))
    })
    @GetMapping({"/future-value", "/present-value/"})
    public ResponseEntity<FutureValueFormulaOutputDTO> applyFutureValueFormula(
            @RequestParam(required = false) Double futureValue,
    		@RequestParam(required = false) Double presentValue,
    		@RequestParam(required = false) Double interestRate,
    		@RequestParam(required = false) Double time) {
    	
		FutureValueFormulaOutput futureValueFormulaOutput 
            = this.futureValueFormulaService.applyFormula(
                futureValue,
                presentValue,
                interestRate,
                time);
		
        return ResponseEntity.ok(futureValueFormulaOutput.toDTO());
    }
    
    /**
     * Calculates the present value of a cash flow.
     *
     * Reference: Section 2.1.1 of the 12th edition of "Matemática Financeira e Suas Aplicações" by Alexandre Assaf Neto.
     * 
     * @return The present value of the cash flow.
     */
    @ApiResponses(value = {
        @ApiResponse(
		responseCode = "200", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = FlowOutputDTO.class),
            examples = @ExampleObject(
                value = "{\"value\": 145776.15, \"time\": 0}")))
    })
    @PostMapping("/present-value/cash-flow")
    public ResponseEntity<FlowOutputDTO> calculatePresentValueOfACashFlow(
            @Valid @RequestBody PresentValueCashFlowInputDTO presentValueCashFlowInputDTO) {
    	
        PresentValueCashFlowInput presentValueCashFlowInput 
            = presentValueCashFlowInputDTO.toModel();
		
		Double presentValue = this.presentValueCashFlowService.calculatePresentValueOfACashFlow(
            presentValueCashFlowInput.getFlows(),
            presentValueCashFlowInput.getInterestRate());
		
		return ResponseEntity.ok(
            ObjectFactoryDTO.getFlowOutputDTO(
                presentValue,
                Double.valueOf(0)));
    }
    
    /**
     * Calculates the equivalent interest rate from the original to the target capitalization period.
     *
     * Reference: Sections 2.2 and 2.3 of the 12th edition of "Matemática Financeira e Suas Aplicações" by Alexandre Assaf Neto.
     * 
     * @param interestRate  the interest rate for the original capitalization period
     * @param from          the original capitalization period
     * @param to            the target capitalization period
     * @return the equivalent interest rate for the target capitalization period
     */
    @ApiResponses(value = {
        @ApiResponse(
		responseCode = "200", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = EquivalentInterestRateOutputDTO.class),
            examples = @ExampleObject(
                value = "{\"interestRate\": 0.0166, \"capitalizationPeriod\": \"MONTHLY\"}")))
    })  
    @GetMapping({"/equivalent-interest-rate", "/effective-interest-rate"})
    public ResponseEntity<EquivalentInterestRateOutputDTO> calculateEquivalentInterestRate(
    		@RequestParam(required = true) Double interestRate,
    		@RequestParam(required = true) CapitalizationPeriod from,
    		@RequestParam(required = true) CapitalizationPeriod to) {
    	
        Double equivalentInterestRate 
            = equivalentCompoundInterestRateService.calculateEquivalentInterestRate(
        		interestRate, 
        		from, 
        		to);

        return ResponseEntity.ok(
            ObjectFactoryDTO.getEquivalentInterestRateOutputDTO(
                equivalentInterestRate,
                to));
    }
}