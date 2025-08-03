package dev.liaskarllate.finmathly.asset.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.liaskarllate.finmathly.asset.specification.AssetSearchSpecificationBuilder;
import dev.liaskarllate.finmathly.asset.entity.Asset;
import dev.liaskarllate.finmathly.asset.query.AssetSearchFilter;
import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;
import dev.liaskarllate.finmathly.marketindex.repository.MarketIndexRepository;
import dev.liaskarllate.finmathly.modality.entity.Modality;
import dev.liaskarllate.finmathly.modality.repository.ModalityRepository;
import dev.liaskarllate.finmathly.asset.repository.AssetRepository;
import dev.liaskarllate.finmathly.shared.exception.MissingArgumentException;
import dev.liaskarllate.finmathly.shared.exception.RelatedResourceNotFoundException;
import dev.liaskarllate.finmathly.shared.exception.UnmodifiableResourceReferenceException;
import dev.liaskarllate.finmathly.shared.exception.ResourceAlreadyExistsException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AssetService {
    private final AssetRepository assetRepository;
    private final MarketIndexRepository marketIndexRepository;
    private final ModalityRepository modalityRepository;

    public Page<Asset> findByFilter(AssetSearchFilter filter, Pageable pageable) {
        Specification<Asset> spec = AssetSearchSpecificationBuilder.build(filter);
        return this.assetRepository.findAll(spec, pageable);
    }

    public Asset findById(UUID id) {
        return this.assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The asset with the id " + id + " was not found."));
    }

    @Transactional
    public Asset save(Asset asset) {
        this.ensureAssetCanBeSaved(asset);
        return this.assetRepository.save(asset);
    }

    private void ensureAssetCanBeSaved(Asset asset) {
        this.checkAssetInexistence(asset);
        this.checkModalityExistence(asset.getModality());
        this.checkMarketIndexExistence(asset.getMarketIndex());
    }

    @Transactional
    public Asset update(Asset asset) {
        this.ensureAssetCanBeUpdated(asset);
        return this.assetRepository.save(asset);
    }

    private void ensureAssetCanBeUpdated(Asset asset) {
        this.checkAssetExistence(asset.getId());
        Asset oldEntity = this.findById(asset.getId());
        this.checkAssetNameUniquenessOnUpdate(asset, oldEntity);
        this.checkAssetModalityImmutabilityOnUpdate(asset.getModality(), oldEntity.getModality());
        this.checkMarketIndexExistence(asset.getMarketIndex());
    }

    @Transactional
    public void deleteById(UUID id) {
        this.ensureAssetCanBeDeleted(id);
        this.assetRepository.deleteById(id);
    }

    private void ensureAssetCanBeDeleted(UUID id) {
        this.checkAssetExistence(id);
    }

    private void checkAssetModalityImmutabilityOnUpdate(Modality newEntity, Modality oldEntity) {
        boolean hasName = newEntity.getName() != null && !newEntity.getName().isBlank();
        boolean hasId = newEntity.getId() != null && !newEntity.getId().toString().isBlank();

        if (!hasName && !hasId) {
            return;
        }

        if ((hasName && !oldEntity.getName().equals(newEntity.getName())) ||
                (hasId && !oldEntity.getId().equals(newEntity.getId()))) {
            throw new UnmodifiableResourceReferenceException(
                    "The asset modality cannot be changed.");
        }
    }

    private void checkAssetNameUniquenessOnUpdate(Asset newEntity, Asset oldEntity) {
        if (!oldEntity.getName().equals(newEntity.getName()) &&
                this.assetRepository.existsByName(newEntity.getName())) {
            throw new ResourceAlreadyExistsException("The asset with the name '" + newEntity.getName()
                    + "' already exists. Please, provide a different name.");
        }
    }

    private void checkModalityExistence(Modality modality) {
        boolean hasName = modality.getName() != null && !modality.getName().isBlank();
        boolean hasId = modality.getId() != null && !modality.getId().toString().isBlank();

        if (hasName && !modalityRepository.existsByName(modality.getName())) {
            throw new RelatedResourceNotFoundException(
                    "The asset modality with the name '" + modality.getName() + "' was not found.");
        }

        if (hasId && !modalityRepository.existsById(modality.getId())) {
            throw new RelatedResourceNotFoundException(
                    "The asset modality with the id '" + modality.getId() + "' was not found.");
        }

        throw new MissingArgumentException("The asset modality is missing. Please, provide a valid modality.");
    }

    private void checkMarketIndexExistence(MarketIndex marketIndex) {
        boolean hasName = marketIndex.getName() != null && !marketIndex.getName().isBlank();
        boolean hasId = marketIndex.getId() != null && !marketIndex.getId().toString().isBlank();

        if (hasName && !marketIndexRepository.existsByName(marketIndex.getName())) {
            throw new RelatedResourceNotFoundException(
                    "The asset market index with the name '" + marketIndex.getName() + "' was not found.");
        }

        if (hasId && !marketIndexRepository.existsById(marketIndex.getId())) {
            throw new RelatedResourceNotFoundException(
                    "The asset market index with the id '" + marketIndex.getId() + "' was not found.");
        }
    }

    private void checkAssetInexistence(Asset asset) {
        if (this.assetRepository.existsByName(asset.getName())) {
            throw new ResourceAlreadyExistsException(
                    "The asset with the name '" + asset.getName() + "' already exists.");
        }
    }

    private void checkAssetExistence(UUID id) {
        if (!this.assetRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException(
                    "The asset with the id '" + id + "' was not found.");
        }
    }
}