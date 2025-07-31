package dev.liaskarllate.finmathly.flow.query;

import java.math.BigDecimal;

import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.entity.Flow_;
import dev.liaskarllate.finmathly.shared.exception.ImpossibleStateException;
import jakarta.persistence.metamodel.SingularAttribute;

public enum FlowSumField {
    amount,
    amortizationPercentage;

    public SingularAttribute<Flow, BigDecimal> getSingularAttribute() {
        switch (this) {
            case amount:
                return Flow_.amount;
            case amortizationPercentage:
                return Flow_.amortizationPercentage;
            default:
                throw new ImpossibleStateException();
        }
    }
}
