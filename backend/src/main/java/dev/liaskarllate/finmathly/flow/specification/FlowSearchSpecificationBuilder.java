package dev.liaskarllate.finmathly.flow.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import dev.liaskarllate.finmathly.asset.entity.Asset;
import dev.liaskarllate.finmathly.asset.entity.Asset_;
import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.entity.Flow_;
import dev.liaskarllate.finmathly.flow.query.FlowSearchFilter;
import dev.liaskarllate.finmathly.shared.enums.EventType;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlowSearchSpecificationBuilder {
    public static Specification<Flow> build(FlowSearchFilter filter) {
        return Specification
                .where(assetNameLike(filter.getAssetName()))
                .and(typeEqual(filter.getType()))
                .and(eventDateEqual(filter.getEventDate()));
    }

    private static Specification<Flow> assetNameLike(String assetName) {
        return (Root<Flow> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (assetName == null || assetName.isBlank()) {
                return cb.conjunction();
            }
            Join<Flow, Asset> join = root.join(Flow_.asset);
            return cb.like(
                    cb.lower(join.get(Asset_.name)),
                    "%" + assetName.toLowerCase() + "%");
        };
    }

    private static Specification<Flow> typeEqual(EventType type) {
        return (Root<Flow> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (type == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(Flow_.type), type);
        };
    }

    private static Specification<Flow> eventDateEqual(LocalDate date) {
        return (Root<Flow> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(Flow_.eventDate), date);
        };
    }

}
