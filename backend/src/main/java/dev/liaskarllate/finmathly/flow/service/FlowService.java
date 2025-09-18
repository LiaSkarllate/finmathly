package dev.liaskarllate.finmathly.flow.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.liaskarllate.finmathly.asset.repository.AssetRepository;
import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.query.FlowSearchFilter;
import dev.liaskarllate.finmathly.flow.query.FlowSumField;
import dev.liaskarllate.finmathly.flow.repository.FlowRepository;
import dev.liaskarllate.finmathly.flow.specification.FlowSearchSpecificationBuilder;
import dev.liaskarllate.finmathly.shared.exception.UnmodifiableResourceReferenceException;
import dev.liaskarllate.finmathly.shared.exception.ResourceAlreadyExistsException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FlowService {
    private final FlowRepository flowRepository;
    private final AssetRepository assetRepository;

    public Page<Flow> findByFilter(FlowSearchFilter filter, Pageable pageable) {
        Specification<Flow> spec = FlowSearchSpecificationBuilder.build(filter);
        return this.flowRepository.findAll(spec, pageable);
    }

    public Flow findById(UUID id) {
        return this.flowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The flow with the id " + id + " was not found."));
    }

    @Transactional
    public Flow save(Flow flow) {
        this.ensureFlowCanBeSaved(flow);
        return this.flowRepository.save(flow);
    }

    private void ensureFlowCanBeSaved(Flow flow) {
        this.checkAssetExistence(flow.getAsset().getName());
    }

    @Transactional
    public Flow update(Flow flow) {
        this.ensureFlowCanBeUpdated(flow);
        return this.flowRepository.save(flow);
    }

    private void ensureFlowCanBeUpdated(Flow flow) {
        this.checkFlowExistence(flow.getId());
        Flow oldEntity = this.findById(flow.getId());
        this.checkFlowAssetImmutabilityOnUpdate(flow.getAsset().getName(), oldEntity.getAsset().getName());
    }

    @Transactional
    public void deleteById(UUID id) {
        this.ensureFlowCanBeDeleted(id);
        this.flowRepository.deleteById(id);
    }

    private void ensureFlowCanBeDeleted(UUID id) {
        this.checkFlowExistence(id);
    }

    public BigDecimal sumAmountByFilter(FlowSearchFilter filter) {
        Specification<Flow> spec = FlowSearchSpecificationBuilder.build(filter);
        return this.flowRepository.sumAmountByFilter(spec);
    }

    public BigDecimal sumByFieldAndFilter(
            FlowSearchFilter filter,
            FlowSumField fieldToSum) {
        Specification<Flow> spec = FlowSearchSpecificationBuilder.build(filter);
        return this.flowRepository.sumByFieldAndFilter(spec, fieldToSum);
    }

    private void checkAssetExistence(String assetName) {
        if (!this.assetRepository.existsByName(assetName)) {
            throw new ResourceAlreadyExistsException(
                    "The asset with the name '" + assetName + "' does not exists.");
        }
    }

    private void checkFlowAssetImmutabilityOnUpdate(String newAssetName, String oldAssetName) {
        if (!newAssetName.equals(oldAssetName)) {
            throw new UnmodifiableResourceReferenceException(
                    "The flow asset can not be changed.");
        }
    }

    public void checkFlowExistence(UUID id) {
        if (!this.flowRepository.existsById(id)) {
            throw new ResourceNotFoundException("The flow with the id " + id + " was not found.");
        }
    }
}