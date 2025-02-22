package dev.liaskarllate.finmathly.service.interest.simple;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;
import dev.liaskarllate.finmathly.service.interest.BaseEquivalentInterestRateService;

/**
 * Service class for calculating equivalent simple interest rates.
 */
@Service("simpleEquivalentInterestRateService")
public class EquivalentInterestRateService extends BaseEquivalentInterestRateService {
    @Override
    protected Double calculate(
            Double interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to) {
        Double conversionFactor = to.getFactor() / from.getFactor();
        return interestRate * conversionFactor;
    }
}