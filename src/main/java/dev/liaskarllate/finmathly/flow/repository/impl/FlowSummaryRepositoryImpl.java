package dev.liaskarllate.finmathly.flow.repository.impl;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.entity.Flow_;
import dev.liaskarllate.finmathly.flow.query.FlowSumField;
import dev.liaskarllate.finmathly.flow.repository.FlowSummaryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Flow> flowRoot = criteriaQuery.from(Flow.class);

        criteriaQuery.select(this.coalesceSum(criteriaBuilder, flowRoot, fieldToSum.getSingularAttribute()));

        this.applySpecificationFilter(spec, criteriaBuilder, criteriaQuery, flowRoot);

        return this.entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    @Override
    public BigDecimal sumAmountByFilter(Specification<Flow> spec) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Flow> flowRoot = criteriaQuery.from(Flow.class);

        criteriaQuery.select(this.coalesceSum(criteriaBuilder, flowRoot, Flow_.amount));

        this.applySpecificationFilter(spec, criteriaBuilder, criteriaQuery, flowRoot);

        return this.entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    private Expression<BigDecimal> coalesceSum(CriteriaBuilder criteriaBuilder, Root<Flow> root,
            SingularAttribute<Flow, BigDecimal> singularAttribute) {
        Expression<BigDecimal> sumExpression = criteriaBuilder.sum(root.get(singularAttribute));
        return criteriaBuilder.coalesce(sumExpression, BigDecimal.ZERO);
    }

    private void applySpecificationFilter(
            Specification<Flow> spec,
            CriteriaBuilder builder,
            CriteriaQuery<BigDecimal> query,
            Root<Flow> root) {
        if (spec != null) {
            Predicate filterPredicate = spec.toPredicate(root, query, builder);
            if (filterPredicate != null) {
                query.where(filterPredicate);
            }
        }
    }
}