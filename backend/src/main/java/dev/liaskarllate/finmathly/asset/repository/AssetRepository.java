package dev.liaskarllate.finmathly.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

import dev.liaskarllate.finmathly.asset.entity.Asset;

public interface AssetRepository extends JpaRepository<Asset, UUID>, JpaSpecificationExecutor<Asset> {
    boolean existsByName(String name);
}