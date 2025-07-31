package dev.liaskarllate.finmathly.marketindex.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;

public interface MarketIndexRepository extends JpaRepository<MarketIndex, UUID>, JpaSpecificationExecutor<MarketIndex> {
    boolean existsByName(String name);
}
