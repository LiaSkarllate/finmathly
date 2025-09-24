package dev.liaskarllate.finmathly.flow.repository;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.entity.Flow_;
import dev.liaskarllate.finmathly.flow.query.FlowSumField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

@Repository
@Transactional(readOnly = true)
public class FlowSummaryRepositoryImpl implements FlowSummaryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BigDecimal sumByFieldAndFilter(Specification<Flow> spec, FlowSumField fieldToSum) {
        Objects.requireNonNull(fieldToSum, "fieldToSum must not be null");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
        Root<Flow> root = query.from(Flow.class);

        query.select(coalesceSum(cb, root, fieldToSum.getSingularAttribute()));
        applySpecificationFilter(spec, cb, query, root);

        TypedQuery<BigDecimal> typed = entityManager.createQuery(query);
        BigDecimal result = typed.getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumAmountByFilter(Specification<Flow> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
        Root<Flow> root = query.from(Flow.class);

        query.select(coalesceSum(cb, root, Flow_.amount));
        applySpecificationFilter(spec, cb, query, root);

        TypedQuery<BigDecimal> typed = entityManager.createQuery(query);
        BigDecimal result = typed.getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    private Expression<BigDecimal> coalesceSum(CriteriaBuilder cb, Root<Flow> root,
            SingularAttribute<Flow, BigDecimal> attr) {
        Expression<BigDecimal> sumExpr = cb.sum(root.get(attr));
        return cb.coalesce(sumExpr, BigDecimal.ZERO);
    }

    private void applySpecificationFilter(
            Specification<Flow> spec,
            CriteriaBuilder builder,
            CriteriaQuery<?> query,
            Root<Flow> root) {

        if (spec == null) {
            return;
        }

        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
    }
}
