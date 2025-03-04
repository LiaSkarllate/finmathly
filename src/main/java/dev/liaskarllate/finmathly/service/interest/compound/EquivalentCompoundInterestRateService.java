package dev.liaskarllate.finmathly.service.interest.compound;

import org.springframework.stereotype.Service;

import dev.liaskarllate.finmathly.model.interest.compound.CapitalizationPeriod;
import dev.liaskarllate.finmathly.service.interest.BaseEquivalentInterestRateService;

/**
 * Service class for calculating equivalent compound interest rates.
 */
@Service
public class EquivalentCompoundInterestRateService extends BaseEquivalentInterestRateService {
    @Override
    protected Double calculate(
            Double interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to) {
        Double conversionFactor = to.getFactor() / from.getFactor();
        return Math.pow(1 + interestRate, conversionFactor) - 1;
    }
}