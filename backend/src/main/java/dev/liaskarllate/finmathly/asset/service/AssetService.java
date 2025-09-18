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
import dev.liaskarllate.finmathly.modality.service.ModalityService;
import dev.liaskarllate.finmathly.asset.repository.AssetRepository;
import dev.liaskarllate.finmathly.shared.enums.YieldType;
import dev.liaskarllate.finmathly.shared.exception.MissingArgumentException;
import dev.liaskarllate.finmathly.shared.exception.RelatedResourceNotFoundException;
import dev.liaskarllate.finmathly.shared.exception.UnmodifiableResourceReferenceException;
import dev.liaskarllate.finmathly.shared.exception.ResourceAlreadyExistsException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AssetService {
    private final ModalityService modalityService;

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

    public Asset findByName(String name) {
        return this.assetRepository.findByName(name)
                .orElseThrow(
                        () -> new ResourceNotFoundException("The asset with the name " + name + " was not found."));
    }

    @Transactional
    public Asset save(Asset asset) {
        this.ensureAssetCanBeSaved(asset);
        return this.assetRepository.save(asset);
    }

    private void ensureAssetCanBeSaved(Asset asset) {
        this.checkAssetNonexistence(asset.getName());
        this.checkModalityExistence(asset.getModality());
        this.checkAssetModalityConstraintsCompliance(asset);
    }

    @Transactional
    public Asset update(Asset asset) {
        this.ensureAssetCanBeUpdated(asset);
        return this.assetRepository.save(asset);
    }

    private void ensureAssetCanBeUpdated(Asset asset) {
        this.checkAssetExistence(asset.getId());
        Asset oldEntity = this.findById(asset.getId());
        this.checkAssetNameUniquenessOnUpdate(asset.getName(), oldEntity.getName());
        this.checkAssetModalityImmutabilityOnUpdate(asset.getModality(), oldEntity.getModality());
        this.checkAssetModalityConstraintsCompliance(asset);
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
        if (!newEntity.hasName() && !newEntity.hasId()) {
            return;
        }

        if ((newEntity.hasName() && !oldEntity.getName().equals(newEntity.getName())) ||
                (newEntity.hasId() && !oldEntity.getId().equals(newEntity.getId()))) {
            throw new UnmodifiableResourceReferenceException(
                    "The asset modality cannot be changed.");
        }
    }

    private void checkAssetNameUniquenessOnUpdate(String newName, String oldName) {
        if (!oldName.equals(newName) &&
                this.assetRepository.existsByName(newName)) {
            throw new ResourceAlreadyExistsException("The asset with the name '" + newName
                    + "' already exists. Please, provide a different name.");
        }
    }

    private void checkModalityExistence(Modality modality) {
        if (modality.hasName() && !modalityRepository.existsByName(modality.getName())) {
            throw new RelatedResourceNotFoundException(
                    "The asset modality with the name '" + modality.getName() + "' was not found.");
        }

        if (modality.hasId() && !modalityRepository.existsById(modality.getId())) {
            throw new RelatedResourceNotFoundException(
                    "The asset modality with the id '" + modality.getId() + "' was not found.");
        }

        if (!modality.hasName() && !modality.hasId()) {
            throw new MissingArgumentException("The asset modality is missing. Please, provide a valid modality.");
        }
    }

    private void checkMarketIndexExistence(MarketIndex marketIndex) {
        if (marketIndex.hasName() && !marketIndexRepository.existsByName(marketIndex.getName())) {
            throw new RelatedResourceNotFoundException(
                    "The asset market index with the name '" + marketIndex.getName() + "' was not found.");
        }

        if (marketIndex.hasId() && !marketIndexRepository.existsById(marketIndex.getId())) {
            throw new RelatedResourceNotFoundException(
                    "The asset market index with the id '" + marketIndex.getId() + "' was not found.");
        }

        if (!marketIndex.hasName() && !marketIndex.hasId()) {
            throw new MissingArgumentException(
                    "The asset market index is missing, which is required for floating or hybrid modalities. Please, provide the asset market index.");
        }
    }

    private void checkAssetNonexistence(String name) {
        if (this.assetRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException(
                    "The asset with the name '" + name + "' already exists.");
        }
    }

    private void checkAssetExistence(UUID id) {
        if (!this.assetRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException(
                    "The asset with the id '" + id + "' was not found.");
        }
    }

    private void checkAssetModalityConstraintsCompliance(Asset asset) {
        Modality assetModality;

        if (asset.getModality().hasName()) {
            assetModality = this.modalityService.findByName(asset.getModality().getName());
        } else {
            assetModality = this.modalityService.findById(asset.getModality().getId());
        }

        assetModality.getYieldType().validate(asset);

        if (assetModality.getYieldType() == YieldType.FLOATING
                || assetModality.getYieldType() == YieldType.HYBRID) {
            this.checkMarketIndexExistence(asset.getMarketIndex());
        }
    }
}