package dev.liaskarllate.finmathly.marketindex.specification;

import org.springframework.data.jpa.domain.Specification;

import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;
import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex_;
import dev.liaskarllate.finmathly.marketindex.query.MarketIndexSearchFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketIndexSearchSpecificationBuilder {
    public static Specification<MarketIndex> build(MarketIndexSearchFilter filter) {
        return Specification
                .where(nameLike(filter.getName()));
    }

    private static Specification<MarketIndex> nameLike(String name) {
        return (Root<MarketIndex> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get(MarketIndex_.name)),
                    "%" + name.toLowerCase() + "%");
        };
    }
}
