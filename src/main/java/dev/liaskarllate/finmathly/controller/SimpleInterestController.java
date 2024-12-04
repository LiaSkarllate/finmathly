package dev.liaskarllate.finmathly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.dto.ObjectFactoryDTO;
import dev.liaskarllate.finmathly.dto.interest.simple.EquivalenceCashFlowInputDTO;
import dev.liaskarllate.finmathly.model.interest.simple.AmountFormulaOutput;
import dev.liaskarllate.finmathly.model.interest.simple.EquivalenceCashFlowInput;
import dev.liaskarllate.finmathly.model.interest.simple.FormulaOutput;
import dev.liaskarllate.finmathly.service.interest.simple.AmountFormulaService;
import dev.liaskarllate.finmathly.service.interest.simple.EquivalenceCashFlowService;
import dev.liaskarllate.finmathly.service.interest.simple.FormulaService;

@RestController
@RequestMapping("/calculations/interest/simple")
public class SimpleInterestController {
	@Autowired
	private FormulaService formulaService;
	
    @Autowired
    private AmountFormulaService amountFormulaService;
    
    @Autowired
    private EquivalenceCashFlowService equivalenceCashFlowService;

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
    
    @GetMapping("/amount")
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
    public ResponseEntity<?> applyEquivalenceCashFlow(EquivalenceCashFlowInputDTO equivalenceCashFlowInputDTO) {
    	
    	EquivalenceCashFlowInput equivalenceCashFlowInput = equivalenceCashFlowInputDTO.toModel();
    	
    	try {
    		Double valueOfInterest = this.equivalenceCashFlowService.applyEquivalence(
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
}