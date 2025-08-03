package dev.liaskarllate.finmathly.marketindex.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.liaskarllate.finmathly.marketindex.specification.MarketIndexSearchSpecificationBuilder;
import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;
import dev.liaskarllate.finmathly.marketindex.query.MarketIndexSearchFilter;
import dev.liaskarllate.finmathly.marketindex.repository.MarketIndexRepository;
import dev.liaskarllate.finmathly.shared.exception.ResourceAlreadyExistsException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MarketIndexService {
    private final MarketIndexRepository marketIndexRepository;

    public Page<MarketIndex> findByFilter(MarketIndexSearchFilter filter, Pageable pageable) {
        Specification<MarketIndex> spec = MarketIndexSearchSpecificationBuilder.build(filter);
        return this.marketIndexRepository.findAll(spec, pageable);
    }

    public MarketIndex findById(UUID id) {
        return this.marketIndexRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The market index with the id " + id + " was not found."));
    }

    @Transactional
    public MarketIndex save(MarketIndex marketIndex) {
        this.ensureMarketIndexCanBeSaved(marketIndex);
        return this.marketIndexRepository.save(marketIndex);
    }

    private void ensureMarketIndexCanBeSaved(MarketIndex marketIndex) {
        this.checkMarketIndexInexistence(marketIndex);
    }

    @Transactional
    public MarketIndex update(MarketIndex marketIndex) {
        this.ensureMarketIndexCanBeUpdated(marketIndex);
        return this.marketIndexRepository.save(marketIndex);
    }

    private void ensureMarketIndexCanBeUpdated(MarketIndex marketIndex) {
        this.checkMarketIndexExistence(marketIndex.getId());
        MarketIndex oldEntity = this.findById(marketIndex.getId());
        this.checkMarketIndexNameUniquenessOnUpdate(marketIndex, oldEntity);
    }

    @Transactional
    public void deleteById(UUID id) {
        this.ensureMarketIndexCanBeDeleted(id);
        this.marketIndexRepository.deleteById(id);
    }

    private void ensureMarketIndexCanBeDeleted(UUID id) {
        this.checkMarketIndexExistence(id);
    }

    private void checkMarketIndexInexistence(MarketIndex marketIndex) {
        if (this.marketIndexRepository.existsByName(marketIndex.getName())) {
            throw new ResourceAlreadyExistsException(
                    "The market index with the name '" + marketIndex.getName() + "' already exists.");
        }
    }

    private void checkMarketIndexNameUniquenessOnUpdate(MarketIndex newEntity, MarketIndex oldEntity) {
        if (!oldEntity.getName().equals(newEntity.getName()) &&
                this.marketIndexRepository.existsByName(newEntity.getName())) {
            throw new ResourceAlreadyExistsException("The market index with the name '" + newEntity.getName()
                    + "' already exists. Please, provide a different name.");
        }
    }

    private void checkMarketIndexExistence(UUID id) {
        if (!this.marketIndexRepository.existsById(id)) {
            throw new ResourceNotFoundException("The market index with the id " + id + " was not found.");
        }
    }
}