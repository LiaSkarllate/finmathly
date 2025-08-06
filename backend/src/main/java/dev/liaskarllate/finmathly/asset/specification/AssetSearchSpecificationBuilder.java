package dev.liaskarllate.finmathly.asset.specification;

import org.springframework.data.jpa.domain.Specification;

import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;
import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex_;
import dev.liaskarllate.finmathly.modality.entity.Modality;
import dev.liaskarllate.finmathly.modality.entity.Modality_;
import dev.liaskarllate.finmathly.asset.entity.Asset;
import dev.liaskarllate.finmathly.asset.entity.Asset_;
import dev.liaskarllate.finmathly.asset.query.AssetSearchFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetSearchSpecificationBuilder {
    public static Specification<Asset> build(AssetSearchFilter filter) {
        return Specification.where(nameLike(filter.getName()))
                .and(resolveModalityByIdOrName(filter))
                .and(maturityDateEqual(filter.getMaturityDate()))
                .and(resolveMarketIndexByIdOrName(filter));
    }

    private static Specification<Asset> resolveModalityByIdOrName(AssetSearchFilter filter) {
        if (filter.getModalityName() != null && !filter.getModalityName().isBlank()) {
            return modalityNameLike(filter.getModalityName());
        }

        return modalityIdEqual(filter.getModalityId());
    }

    private static Specification<Asset> resolveMarketIndexByIdOrName(AssetSearchFilter filter) {
        if (filter.getMarketIndexName() != null && !filter.getMarketIndexName().isBlank()) {
            return marketIndexNameLike(filter.getMarketIndexName());
        }

        return marketIndexIdEqual(filter.getMarketIndexId());
    }

    private static Specification<Asset> nameLike(String name) {
        return (Root<Asset> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get(Asset_.name)),
                    "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Asset> modalityIdEqual(UUID modalityId) {
        return (Root<Asset> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (modalityId == null || modalityId.toString().isBlank()) {
                return cb.conjunction();
            }
            Join<Asset, Modality> join = root.join(Asset_.modality);
            return cb.equal(
                    join.get(Modality_.id),
                    modalityId);
        };
    }

    private static Specification<Asset> modalityNameLike(String modalityName) {
        return (Root<Asset> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (modalityName == null || modalityName.isBlank()) {
                return cb.conjunction();
            }
            Join<Asset, Modality> join = root.join(Asset_.modality);
            return cb.like(
                    cb.lower(join.get(Modality_.name)),
                    "%" + modalityName.toLowerCase() + "%");
        };
    }

    public static Specification<Asset> marketIndexIdEqual(UUID marketIndexId) {
        return (Root<Asset> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (marketIndexId == null || marketIndexId.toString().isBlank()) {
                return cb.conjunction();
            }
            Join<Asset, MarketIndex> join = root.join(Asset_.marketIndex);
            return cb.equal(
                    join.get(MarketIndex_.id),
                    marketIndexId);
        };
    }

    private static Specification<Asset> marketIndexNameLike(String marketIndexName) {
        return (Root<Asset> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (marketIndexName == null || marketIndexName.isBlank()) {
                return cb.conjunction();
            }
            Join<Asset, MarketIndex> join = root.join(Asset_.marketIndex);
            return cb.like(
                    cb.lower(join.get(MarketIndex_.name)),
                    "%" + marketIndexName.toLowerCase() + "%");
        };
    }

    private static Specification<Asset> maturityDateEqual(LocalDate date) {
        return (Root<Asset> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.equal(
                    root.get(Asset_.maturityDate),
                    date);
        };
    }
}
