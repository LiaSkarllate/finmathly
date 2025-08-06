package dev.liaskarllate.finmathly.flow.repository;

import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;
import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.query.FlowSumField;

public interface FlowSummaryRepository {
    BigDecimal sumAmountByFilter(Specification<Flow> spec);

    BigDecimal sumByFieldAndFilter(Specification<Flow> spec, FlowSumField fieldToSum);
}
