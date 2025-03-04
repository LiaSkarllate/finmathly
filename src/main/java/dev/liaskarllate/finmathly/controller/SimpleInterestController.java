package dev.liaskarllate.finmathly.controller;

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
import dev.liaskarllate.finmathly.dto.interest.simple.AmountFormulaOutputDTO;
import dev.liaskarllate.finmathly.dto.interest.simple.EquivalenceCashFlowInputDTO;
import dev.liaskarllate.finmathly.dto.interest.simple.SimpleInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;
import dev.liaskarllate.finmathly.model.interest.simple.AmountFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.simple.EquivalenceCashFlowInput;
import dev.liaskarllate.finmathly.model.interest.simple.SimpleInterestFormulaOutput;
import dev.liaskarllate.finmathly.service.interest.simple.AmountFormulaService;
import dev.liaskarllate.finmathly.service.interest.simple.EquivalentCashFlowService;
import dev.liaskarllate.finmathly.service.interest.simple.EquivalentSimpleInterestRateService;
import dev.liaskarllate.finmathly.service.interest.simple.SimpleInterestFormulaService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST Controller that exposes service through endpoints for performing simple interest calculations.
 */
@RestController
@Tag(name = "Simple interest calculations")
@RequestMapping("/calculations/interest/simple")
public class SimpleInterestController {
	@Autowired
	private SimpleInterestFormulaService simpleInterestFormulaService;
	
    @Autowired
    private AmountFormulaService amountFormulaService;
    
    @Autowired
    private EquivalentCashFlowService equivalentCashFlowService;
    
    @Autowired
    private EquivalentSimpleInterestRateService equivalentSimpleInterestRateService;

    /**
     * Applies the simple interest formula to calculate a missing parameter.
     * 
     * Reference: Section 1.8 of the 12th edition of "Matemática Financeira e Suas Aplicações" by Alexandre Assaf Neto.
     *
     * @param interest      the interest (can be null if it needs to be calculated)
     * @param principal     the principal (can be null if it needs to be calculated)
     * @param interestRate  the interest rate (can be null if it needs to be calculated)
     * @param time          the time (can be null if it needs to be calculated)
     * @return a body containing all values, including the calculated one
	 */
    @ApiResponses(value = {
        @ApiResponse(
		responseCode = "200", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = SimpleInterestFormulaOutputDTO.class),
            examples = @ExampleObject(
                value = "{\"interest\": 6000.00, \"principal\": 80000.00, \"interestRate\": 0.025, \"time\": 3}")))
    })
    @GetMapping("/interest")
    public ResponseEntity<SimpleInterestFormulaOutputDTO> applyInterestFormula(
    		@RequestParam(required = false) Double interest,
    		@RequestParam(required = false) Double principal,
    		@RequestParam(required = false) Double interestRate,
    		@RequestParam(required = false) Double time) {
    	
		SimpleInterestFormulaOutput simpleInterestFormulaOutput 
            = this.simpleInterestFormulaService.applyFormula(
				interest,
				principal,
				interestRate,
				time);
		
        return ResponseEntity.ok(simpleInterestFormulaOutput.toDTO());
    }
    
    /**
     * Applies the simple interest amount formula to calculate a missing parameter.
     *
     * Reference: Section 1.9 of the 12th edition of "Matemática Financeira e Suas Aplicações" by Alexandre Assaf Neto.
     * 
     * @param amount        the amount (can be null if it needs to be calculated)
     * @param principal     the principal amount (can be null if it needs to be calculated)
     * @param interestRate  the interest rate (can be null if it needs to be calculated)
     * @param time          the time (can be null if it needs to be calculated)
     * @return a body containing all values, including the calculated one
     */
    @ApiResponses(value = {
        @ApiResponse(
		responseCode = "200", 
		content = @Content(
				mediaType = "application/json", 
				schema = @Schema(implementation = AmountFormulaOutputDTO.class),
				examples = @ExampleObject(
                    value = "{\"amount\": 20160.00, \"principal\": 18000.00, \"interestRate\": 0.015, \"time\": 8}")))
    })
    @GetMapping({"/amount", "/principal"})
    public ResponseEntity<AmountFormulaOutputDTO> applyAmountFormula(
    		@RequestParam(required = false) Double amount,
    		@RequestParam(required = false) Double principal,
    		@RequestParam(required = false) Double interestRate,
    		@RequestParam(required = false) Double time) {
    	
		AmountFormulaOutput amountFormulaOutput 
            = this.amountFormulaService.applyFormula(
                amount,
                principal,
                interestRate,
                time);
		
        return ResponseEntity.ok(amountFormulaOutput.toDTO());
    }
    
    /**
     * Calculates the required value of a target flow from a proposed cash flow to balance 
     * an original one, ensuring equivalence.
     * 
     * Reference: Section 1.12 of the 12th edition of "Matemática Financeira e Suas Aplicações" by Alexandre Assaf Neto.
     *
     * @return the target flow with the required value to ensure equivalence
     */
    @ApiResponses(value = {
        @ApiResponse(
		responseCode = "200", 
		content = @Content(
				mediaType = "application/json", 
				schema = @Schema(implementation = FlowOutputDTO.class),
				examples = @ExampleObject(
                    value = "{\"value\": 145776.15, \"time\": 12}")))
    })  
    @PostMapping("/equivalence-cash-flow")
    public ResponseEntity<FlowOutputDTO> calculateRequiredTargetFlowValue(
            @RequestBody @Valid EquivalenceCashFlowInputDTO equivalenceCashFlowInputDTO) {

    	EquivalenceCashFlowInput equivalenceCashFlowInput 
            = equivalenceCashFlowInputDTO.toModel();
    	
        Double targetValue 
            = this.equivalentCashFlowService.calculateRequiredTargetFlowValue(
			    equivalenceCashFlowInput.getOriginalFlows(),
				equivalenceCashFlowInput.getProposedFlows(),
				equivalenceCashFlowInput.getTargetFlow(),
				equivalenceCashFlowInput.getFocalTime(),
				equivalenceCashFlowInput.getInterestRate());
		
        return ResponseEntity.ok(
            ObjectFactoryDTO.getFlowOutputDTO(
                targetValue,
                equivalenceCashFlowInputDTO.getFocalTime()));
    }
    
    /**
     * Calculates the equivalent interest rate from an original to a target capitalization period.
     *
     * Reference: Section 2.2 of the 12th edition of "Matemática Financeira e Suas Aplicações" by Alexandre Assaf Neto.
     * 
     * @param interestRate	the interest rate for the original capitalization period
     * @param from 			the original capitalization period
     * @param to 			the target capitalization period
     * @return the equivalent interest rate for the target capitalization period
     */
    @ApiResponses(value = {
        @ApiResponse(
		responseCode = "200", 
		content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = EquivalentInterestRateOutputDTO.class),
            examples = @ExampleObject(
                value = "{\"interestRate\": 0.09, \"capitalizationPeriod\": \"QUARTERLY\"}")))
    })
    @GetMapping("/equivalent-interest-rate")
    public ResponseEntity<EquivalentInterestRateOutputDTO > calculateEquivalentInterestRate(
            @RequestParam(required = true) Double interestRate,
    		@RequestParam(required = true) CapitalizationPeriod from,
    		@RequestParam(required = true) CapitalizationPeriod to) {
    	
        Double equivalentInterestRate 
            = equivalentSimpleInterestRateService.calculateEquivalentInterestRate(
                interestRate, 
                from, 
                to);

        return ResponseEntity.ok(
            ObjectFactoryDTO.getEquivalentInterestRateOutputDTO(
                equivalentInterestRate,
                to));
    }
}