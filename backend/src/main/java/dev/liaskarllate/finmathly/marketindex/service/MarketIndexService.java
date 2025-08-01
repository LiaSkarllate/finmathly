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
    private final MarketIndexRepository marketindexRepository;

    public Page<MarketIndex> findByFilter(MarketIndexSearchFilter filter, Pageable pageable) {
        Specification<MarketIndex> spec = MarketIndexSearchSpecificationBuilder.build(filter);
        return this.marketindexRepository.findAll(spec, pageable);
    }

    public MarketIndex findById(UUID id) {
        return this.marketindexRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The marketindex with the id " + id + " was not found."));
    }

    @Transactional
    public MarketIndex save(MarketIndex marketindex) {
        this.ensureMarketIndexCanBeSaved(marketindex);
        return this.marketindexRepository.save(marketindex);
    }

    private void ensureMarketIndexCanBeSaved(MarketIndex marketindex) {
        this.checkMarketIndexInexistence(marketindex);
    }

    @Transactional
    public MarketIndex update(MarketIndex marketindex) {
        this.ensureMarketIndexCanBeUpdated(marketindex);
        return this.marketindexRepository.save(marketindex);
    }

    private void ensureMarketIndexCanBeUpdated(MarketIndex marketindex) {
        MarketIndex oldEntity = this.findById(marketindex.getId());
        this.checkMarketIndexNameUniquenessOnUpdate(marketindex, oldEntity);
    }

    @Transactional
    public void deleteById(UUID id) {
        this.ensureMarketIndexCanBeDeleted(id);
        this.marketindexRepository.deleteById(id);
    }

    private void ensureMarketIndexCanBeDeleted(UUID id) {
        this.findById(id);
    }

    private void checkMarketIndexInexistence(MarketIndex marketindex) {
        if (this.marketindexRepository.existsByName(marketindex.getName())) {
            throw new ResourceAlreadyExistsException(
                    "The marketindex with the name '" + marketindex.getName() + "' already exists.");
        }
    }

    private void checkMarketIndexNameUniquenessOnUpdate(MarketIndex newEntity, MarketIndex oldEntity) {
        if (!oldEntity.getName().equals(newEntity.getName()) &&
                this.marketindexRepository.existsByName(newEntity.getName())) {
            throw new ResourceAlreadyExistsException("The marketindex with the name '" + newEntity.getName()
                    + "' already exists. Please, provide a different name.");
        }
    }
}