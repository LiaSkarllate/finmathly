package dev.liaskarllate.finmathly.calculation.interest.simple.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import dev.liaskarllate.finmathly.calculation.interest.shared.service.BaseEquivalentInterestRateService;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;

/**
 * Service class for calculating equivalent simple interest rates.
 */
@Service
public class EquivalentSimpleInterestRateService extends BaseEquivalentInterestRateService {
    @Override
    protected BigDecimal calculate(
            BigDecimal interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to) {

        BigDecimal conversionFactor = to.getFactor().divide(from.getFactor());
        return interestRate.multiply(conversionFactor);
    }

}