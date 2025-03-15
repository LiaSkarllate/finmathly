package dev.liaskarllate.finmathly.service.interest.compound;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.exception.InputNotValidException;
import dev.liaskarllate.finmathly.model.FlowInput;

/**
 * Service class for calculating the present value of a cash flow.
 */
@Service
public class PresentValueCashFlowService {
	@Autowired
    private FutureValueFormulaService futureValueFormulaService;

	public Double calculatePresentValueOfACashFlow(
			List<FlowInput> flows,
			Double interestRate ) {
		
		this.validateProvidedParameters(
				flows, 
				interestRate);
		
		return this.calculate(
				flows,
				interestRate);
	}
	
    private void validateProvidedParameters(
            List<FlowInput> flows,
            Double interestRate) {

        if (flows == null || flows.isEmpty()) {
            throw new InputNotValidException("Flows are required. Please provide a cash flow.");
        }

        if (interestRate == null) {
            throw new InputNotValidException("Interest rate is required. Please provide a value, such as 0.05 for 5%.");
        }
        
        if (interestRate < 0) {
            throw new InputNotValidException("Interest rate cannot be negative. Please provide a positive value, such as 0.05 for 5%.");
        }

        for (FlowInput flow : flows) {
            if (flow.getValue() == null) {
                throw new InputNotValidException("The value of at least one flow has not been provided. Please provide all flow values.");
            }
            
            if (flow.getTime() == null) {
                throw new InputNotValidException("The time of at least one flow has not been provided. Please provide all flow times.");
            }
        }
    }

	private Double calculate(
			List<FlowInput> flows,
			Double interestRate) {
		
		return flows.stream()
            .mapToDouble(flow -> this.futureValueFormulaService.applyFormula(
                flow.getValue(),
                null,
                interestRate,
                flow.getTime()).getPresentValue())
            .sum();
	}
}