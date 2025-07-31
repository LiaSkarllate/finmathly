package dev.liaskarllate.finmathly.modality.specification;

import org.springframework.data.jpa.domain.Specification;

import dev.liaskarllate.finmathly.shared.enums.YieldType;
import dev.liaskarllate.finmathly.modality.entity.Modality;
import dev.liaskarllate.finmathly.modality.entity.Modality_;
import dev.liaskarllate.finmathly.modality.query.ModalitySearchFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModalitySearchSpecificationBuilder {
    public static Specification<Modality> build(ModalitySearchFilter filter) {
        return Specification
                .where(nameLike(filter.getName()))
                .and(nameEquals(filter.getYieldType()));
    }

    private static Specification<Modality> nameEquals(YieldType yieldType) {
        return (Root<Modality> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (yieldType == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(Modality_.yieldType), yieldType);
        };
    }

    private static Specification<Modality> nameLike(String name) {
        return (Root<Modality> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get(Modality_.name)),
                    "%" + name.toLowerCase() + "%");
        };
    }
}
