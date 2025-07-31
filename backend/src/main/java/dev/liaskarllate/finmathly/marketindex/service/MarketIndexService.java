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

    @Transactional(readOnly = true)
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
        if (this.marketindexRepository.existsByName(marketindex.getName())) {
            throw new ResourceAlreadyExistsException("The marketindex with the name '" + marketindex.getName() + "' already exists.");
        }
    }

    @Transactional
    public MarketIndex update(MarketIndex marketindex) {
        this.ensureMarketIndexCanBeUpdated(marketindex);
        return this.marketindexRepository.save(marketindex);
    }

    private void ensureMarketIndexCanBeUpdated(MarketIndex marketindex) {
        MarketIndex existingMarketIndex = this.marketindexRepository.findById(marketindex.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The market index with the id '" + marketindex.getId() + "' was not found. Please, provide a valid id."));

        if (!existingMarketIndex.getName().equals(marketindex.getName()) &&
                this.marketindexRepository.existsByName(marketindex.getName())) {
            throw new ResourceAlreadyExistsException("The marketindex with the name '" + marketindex.getName() + "' already exists. Please, provide a different name.");
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        this.ensureMarketIndexCanBeDeleted(id);
        this.marketindexRepository.deleteById(id);
    }

    private void ensureMarketIndexCanBeDeleted(UUID id) {
        if (!this.marketindexRepository.existsById(id)) {
            throw new ResourceNotFoundException("The marketindex with the id '" + id + "' was not found.");
        }
    }
}