package dev.liaskarllate.finmathly.service.interest.compound;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.exception.IllegalExternalParameterException;
import dev.liaskarllate.finmathly.model.FlowInputOutput;

/**
 * Service class for calculating the present value of a cash flow.
 */
@Service
public class PresentValueCashFlowService {
	@Autowired
    private FutureValueFormulaService futureValueFormulaService;

	/**
     * Calculates the present value of a cash flow.
     *
     * @param flows         a list of {@link FlowInputOutput} objects representing the flows.
     * @param interestRate  the interest rate used for discounting the flows (as a decimal, e.g., 0.05 for 5%).
     * @return The present value of the cash flow as a {@link Double}.
     */
	public Double applyCalculations(
			List<FlowInputOutput> flows,
			Double interestRate ) {
		
		this.validateParametersProvided(
				flows, 
				interestRate);
		
		return this.calculate(
				flows,
				interestRate);
	}
	
    private void validateParametersProvided(
            List<FlowInputOutput> flows,
            Double interestRate) {

        if (flows == null || flows.isEmpty()) {
            throw new IllegalExternalParameterException("Flows are required. Please provide a cash flow.");
        }

        if (interestRate == null) {
            throw new IllegalExternalParameterException("Interest rate is required. Please provide a value, such as 0.05 for 5%.");
        }
        
        if (interestRate < 0) {
            throw new IllegalExternalParameterException("Interest rate cannot be negative. Please provide a positive value, such as 0.05 for 5%.");
        }

        for (FlowInputOutput flow : flows) {
            if (flow.getValue() == null) {
                throw new IllegalExternalParameterException("The value of at least one flow has not been provided. Please provide all cash flow values.");
            }
            
            if (flow.getTime() == null) {
                throw new IllegalExternalParameterException("The time of at least one flow has not been provided. Please provide all cash flow times.");
            }
        }
    }

	/**
     * Performs the calculation of the present value of a cash flow.
     */
	private Double calculate(
			List<FlowInputOutput> flows,
			Double interestRate) {
		
		Double valueOfInterest = flows.stream()
			    .mapToDouble(flow -> this.futureValueFormulaService.applyFormula(
			        flow.getValue(),
			        null,
			        interestRate,
			        flow.getTime()).getPresentValue())
			    .sum();
		
		return valueOfInterest;
	}
}