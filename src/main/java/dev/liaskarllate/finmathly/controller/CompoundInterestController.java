package dev.liaskarllate.finmathly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.dto.ObjectFactoryDTO;
import dev.liaskarllate.finmathly.dto.interest.compound.PresentValueCashFlowInputDTO;
import dev.liaskarllate.finmathly.model.interest.compound.FormulaOutput;
import dev.liaskarllate.finmathly.model.interest.compound.FutureValueFormulaOutput;
import dev.liaskarllate.finmathly.service.interest.compound.FormulaService;
import dev.liaskarllate.finmathly.service.interest.compound.FutureValueFormulaService;

@RestController
@RequestMapping("/calculations/interest/compound")
public class CompoundInterestController {
    @Autowired
    private FormulaService formulaService;
    
    @Autowired
    private FutureValueFormulaService futureValueFormulaService;
    
    @GetMapping("/interest")
    public ResponseEntity<?> applyFormula(
    		@RequestParam(required = false) Double interest,
    		@RequestParam(required = false) Double presentValue, 
    		@RequestParam(required = false) Double interestRate, 
    		@RequestParam(required = false) Double time) {
    	
    	try {
    		FormulaOutput formulaOutput = this.formulaService.applyFormula(
					interest,
					presentValue,
					interestRate,
					time);
			
            return ResponseEntity.ok(formulaOutput.toDTO());
		} catch (Exception exception) {
            return ResponseEntity.badRequest().body(ObjectFactoryDTO.getThrownExceptionDTO(exception.getMessage()));
		}
    }
    
    @GetMapping({"/future-value", "/present-value/"})
    public ResponseEntity<?> applyFutureValueFormula(
    		@RequestParam(required = false) Double futureValue,
    		@RequestParam(required = false) Double presentValue,
    		@RequestParam(required = false) Double interestRate,
    		@RequestParam(required = false) Double time) {
    	
    	try {
    		FutureValueFormulaOutput futureValueFormulaOutput = this.futureValueFormulaService.applyFormula(
    				futureValue,
    				presentValue,
    				interestRate,
    				time);
			
            return ResponseEntity.ok(futureValueFormulaOutput.toDTO());
		} catch (Exception exception) {
            return ResponseEntity.badRequest().body(ObjectFactoryDTO.getThrownExceptionDTO(exception.getMessage()));
		}
    }
    
    @GetMapping("/present-value/cash-flow")
    public ResponseEntity<?> calculatePresentValueCashFlow(PresentValueCashFlowInputDTO presentValueCashFlowInputDTO) {
    	// TODO: Terminar a implementação desse cálculo.
    	return null;
    }
    
}