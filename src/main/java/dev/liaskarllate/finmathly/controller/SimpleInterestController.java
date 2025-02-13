package dev.liaskarllate.finmathly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.dto.ObjectFactoryDTO;
import dev.liaskarllate.finmathly.dto.interest.simple.EquivalenceCashFlowInputDTO;
import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;
import dev.liaskarllate.finmathly.model.interest.simple.AmountFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.simple.EquivalenceCashFlowInput;
import dev.liaskarllate.finmathly.model.interest.simple.FormulaOutput;
import dev.liaskarllate.finmathly.service.interest.simple.AmountFormulaService;
import dev.liaskarllate.finmathly.service.interest.simple.EquivalentCashFlowService;
import dev.liaskarllate.finmathly.service.interest.simple.EquivalentInterestRateService;
import dev.liaskarllate.finmathly.service.interest.simple.FormulaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/calculations/interest/simple")
public class SimpleInterestController {
	@Autowired
	private FormulaService formulaService;
	
    @Autowired
    private AmountFormulaService amountFormulaService;
    
    @Autowired
    private EquivalentCashFlowService equivalentCashFlowService;
    
    @Autowired
    private EquivalentInterestRateService equivalentInterestRateService;

    @GetMapping("/interest")
    public ResponseEntity<?> applyInterestFormula(
    		@RequestParam(required = false) Double interest,
    		@RequestParam(required = false) Double principal,
    		@RequestParam(required = false) Double interestRate,
    		@RequestParam(required = false) Double time) {
    	
    	try {
			FormulaOutput formulaOutput = this.formulaService.applyFormula(
					interest,
					principal,
					interestRate,
					time);
			
            return ResponseEntity.ok(formulaOutput.toDTO());
		} catch (Exception exception) {
            return ResponseEntity.badRequest().body(ObjectFactoryDTO.getThrownExceptionDTO(exception.getMessage()));
		}
    }
    
    @GetMapping({"/amount", "/principal"})
    public ResponseEntity<?> applyAmountFormula(
    		@RequestParam(required = false) Double amount,
    		@RequestParam(required = false) Double principal,
    		@RequestParam(required = false) Double interestRate,
    		@RequestParam(required = false) Double time) {
    	
    	try {
    		AmountFormulaOutput amountFormulaOutput = this.amountFormulaService.applyFormula(
    				amount,
    				principal,
					interestRate,
					time);
			
            return ResponseEntity.ok(amountFormulaOutput.toDTO());
		} catch (Exception exception) {
            return ResponseEntity.badRequest().body(ObjectFactoryDTO.getThrownExceptionDTO(exception.getMessage()));
		}
    }
    
    @GetMapping("/equivalence-cash-flow")
    public ResponseEntity<?> applyEquivalenceCashFlow(@RequestBody @Valid EquivalenceCashFlowInputDTO equivalenceCashFlowInputDTO) {
    	
    	EquivalenceCashFlowInput equivalenceCashFlowInput = equivalenceCashFlowInputDTO.toModel();
    	
    	try {
    		Double valueOfInterest = this.equivalentCashFlowService.applyCalculations(
    				equivalenceCashFlowInput.getOriginalCashFlows(),
    				equivalenceCashFlowInput.getProposedCashFlows(),
    				equivalenceCashFlowInput.getEventOfInterest(),
    				equivalenceCashFlowInput.getFocalTime(),
    				equivalenceCashFlowInput.getInterestRate());
			
            return ResponseEntity.ok(
            		ObjectFactoryDTO.getFlowInputOutputDTO(
            				valueOfInterest,
            				equivalenceCashFlowInputDTO.getFocalTime()));
		} catch (Exception exception) {
            return ResponseEntity.badRequest().body(ObjectFactoryDTO.getThrownExceptionDTO(exception.getMessage()));
		}
    }
    
    @GetMapping("/equivalent-interest-rate")
    public ResponseEntity<?> calculateEquivalentRate(
    		@RequestParam(required = true) Double interestRate,
    		@RequestParam(required = true) CapitalizationPeriod from,
    		@RequestParam(required = true) CapitalizationPeriod to) {
    	try {
	        Double equivalentInterestRate = equivalentInterestRateService.calculateEquivalentInterestRate(
	        		interestRate, 
	        		from, 
	        		to);
	
	        return ResponseEntity.ok(
            		ObjectFactoryDTO.getEquivalentInterestRateOutputDTO(
            				equivalentInterestRate,
            				to));
    	} catch (Exception exception) {
            return ResponseEntity.badRequest().body(ObjectFactoryDTO.getThrownExceptionDTO(exception.getMessage()));
		}
    }
}