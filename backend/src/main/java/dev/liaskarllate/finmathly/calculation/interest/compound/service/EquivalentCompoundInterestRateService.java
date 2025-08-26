package dev.liaskarllate.finmathly.calculation.interest.compound.service;

import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.stereotype.Service;

import ch.obermuhlner.math.big.BigDecimalMath;
import dev.liaskarllate.finmathly.calculation.interest.shared.service.BaseEquivalentInterestRateService;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;

/**
 * Service class for calculating equivalent compound interest rates.
 */
@Service
public class EquivalentCompoundInterestRateService extends BaseEquivalentInterestRateService {
    @Override
    protected BigDecimal calculate(
            BigDecimal interestRate,
            CapitalizationPeriod from,
            CapitalizationPeriod to) {

        BigDecimal conversionFactor = to.getFactor().divide(from.getFactor(), MathContext.DECIMAL64);
        BigDecimal base = BigDecimal.ONE.add(interestRate);
        BigDecimal exponentiated = BigDecimalMath.pow(base, conversionFactor, MathContext.DECIMAL64);

        return exponentiated.subtract(BigDecimal.ONE, MathContext.DECIMAL64);
    }
}