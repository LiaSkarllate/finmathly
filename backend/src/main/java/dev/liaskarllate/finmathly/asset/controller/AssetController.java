package dev.liaskarllate.finmathly.asset.controller;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.asset.mapper.AssetMapper;
import dev.liaskarllate.finmathly.asset.query.AssetSearchFilter;
import dev.liaskarllate.finmathly.asset.query.AssetSearchSortingField;
import dev.liaskarllate.finmathly.asset.dto.AssetDTO;
import dev.liaskarllate.finmathly.asset.entity.Asset;
import dev.liaskarllate.finmathly.asset.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetController {
    private final AssetService assetService;
    private final AssetMapper assetMapper;

    @GetMapping
    public ResponseEntity<Page<AssetDTO>> getAssets(
            @Valid @ModelAttribute AssetSearchFilter filter,
            @RequestParam Optional<AssetSearchSortingField> by,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                by.map(field -> Sort.by(Sort.Direction.DESC, field.toString()))
                        .orElse(Sort.unsorted()));

        Page<AssetDTO> assets = this.assetService
                .findByFilter(filter, pageable)
                .map(this.assetMapper::toDTO);
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getAssetById(@PathVariable UUID id) {
        Asset asset = this.assetService.findById(id);
        return ResponseEntity.ok(this.assetMapper.toDTO(asset));
    }

    @PostMapping
    public ResponseEntity<AssetDTO> saveAsset(@Valid @RequestBody AssetDTO assetDTO) {
        Asset createdAsset = this.assetService.save(this.assetMapper.toEntity(assetDTO));
        return new ResponseEntity<>(this.assetMapper.toDTO(createdAsset), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAsset(@PathVariable UUID id, @Valid @RequestBody AssetDTO assetDTO) {
        Asset updatedAsset = this.assetService.update(assetMapper.toEntity(assetDTO, id));
        return ResponseEntity.ok(this.assetMapper.toDTO(updatedAsset));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetById(@PathVariable UUID id) {
        this.assetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
