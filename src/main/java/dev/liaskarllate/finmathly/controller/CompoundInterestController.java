package dev.liaskarllate.finmathly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.dto.ObjectFactoryDTO;
import dev.liaskarllate.finmathly.dto.interest.compound.PresentValueCashFlowInputDTO;
import dev.liaskarllate.finmathly.model.interest.compound.FormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.FutureValueFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.PresentValueCashFlowInput;
import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;
import dev.liaskarllate.finmathly.service.interest.compound.EquivalentInterestRateService;
import dev.liaskarllate.finmathly.service.interest.compound.FormulaService;
import dev.liaskarllate.finmathly.service.interest.compound.FutureValueFormulaService;
import dev.liaskarllate.finmathly.service.interest.compound.PresentValueCashFlowService;
import jakarta.validation.Valid;

/**
 * REST Controller that exposes service through endpoints for performing compound interest calculations.
 */
@RestController
@RequestMapping("/calculations/interest/compound")
public class CompoundInterestController {
    @Autowired
    private FormulaService formulaService;
    
    @Autowired
    private FutureValueFormulaService futureValueFormulaService;
    
    @Autowired
    private PresentValueCashFlowService presentValueCashFlowService;
    
    @Autowired
    private EquivalentInterestRateService equivalentInterestRateService;
    
    @GetMapping("/interest")
    public ResponseEntity<?> applyInterestFormula(
    		@RequestParam(required = false) Double interest,
    		@RequestParam(required = false) Double presentValue, 
    		@RequestParam(required = false) Double interestRate, 
    		@RequestParam(required = false) Double time) {
    	
		FormulaOutput formulaOutput = this.formulaService.applyFormula(
				interest,
				presentValue,
				interestRate,
				time);
		
        return ResponseEntity.ok(formulaOutput.toDTO());
    }
    
    @GetMapping({"/future-value", "/present-value/"})
    public ResponseEntity<?> applyFutureValueFormula(
    		@RequestParam(required = false) Double futureValue,
    		@RequestParam(required = false) Double presentValue,
    		@RequestParam(required = false) Double interestRate,
    		@RequestParam(required = false) Double time) {
    	
		FutureValueFormulaOutput futureValueFormulaOutput = this.futureValueFormulaService.applyFormula(
				futureValue,
				presentValue,
				interestRate,
				time);
		
        return ResponseEntity.ok(futureValueFormulaOutput.toDTO());
    }
    
    @PostMapping("/present-value/cash-flow")
    public ResponseEntity<?> calculatePresentValueOfCashFlows(@RequestBody @Valid PresentValueCashFlowInputDTO presentValueCashFlowInputDTO) {
		
    	PresentValueCashFlowInput presentValueCashFlowInput = presentValueCashFlowInputDTO.toModel();
		
		Double valueOfInterest = this.presentValueCashFlowService.calculatePresentValueOfCashFlows(
				presentValueCashFlowInput.getFlows(),
				presentValueCashFlowInput.getInterestRate());
		
		return ResponseEntity.ok(
        		ObjectFactoryDTO.getFlowInputOutputDTO(
        				valueOfInterest,
        				Double.valueOf(0)));
    }
    
    @GetMapping({"/equivalent-interest-rate", "/effective-interest-rate"})
    public ResponseEntity<?> calculateEquivalentInterestRate(
    		@RequestParam(required = true) Double interestRate,
    		@RequestParam(required = true) CapitalizationPeriod from,
    		@RequestParam(required = true) CapitalizationPeriod to) {
    	
        Double equivalentInterestRate = equivalentInterestRateService.calculateEquivalentInterestRate(
        		interestRate, 
        		from, 
        		to);

        return ResponseEntity.ok(
        		ObjectFactoryDTO.getEquivalentInterestRateOutputDTO(
        				equivalentInterestRate,
        				to));
    }
}