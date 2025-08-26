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
import dev.liaskarllate.finmathly.shared.dto.ThrownExceptionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
@Tag(name = "Assets", description = "Service responsible for managing assets.")
public class AssetController {
    private final AssetService assetService;
    private final AssetMapper assetMapper;

    @Operation(summary = "List assets", tags = { "Assets" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns a list of assets.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AssetDTO.class)))),
            @ApiResponse(responseCode = "204", description = "No content. There are no assets to return.")
    })
    @GetMapping
    public ResponseEntity<Page<AssetDTO>> listAssets(
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

    @Operation(summary = "Get asset", tags = { "Assets" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the asset.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetDTO.class))),
            @ApiResponse(responseCode = "204", description = "No content. No asset found for the given id to return."),
            @ApiResponse(responseCode = "400", description = "Bad request.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getAsset(@PathVariable UUID id) {
        Asset asset = this.assetService.findById(id);
        return ResponseEntity.ok(this.assetMapper.toDTO(asset));
    }

    @Operation(summary = "Create asset", tags = { "Assets" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created. Returns the created asset.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @PostMapping
    public ResponseEntity<AssetDTO> createAsset(@Valid @RequestBody AssetDTO assetDTO) {
        Asset createdAsset = this.assetService.save(this.assetMapper.toEntity(assetDTO));
        return new ResponseEntity<>(this.assetMapper.toDTO(createdAsset), HttpStatus.CREATED);
    }

    @Operation(summary = "Update asset", tags = { "Assets" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the updated asset.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAsset(@PathVariable UUID id, @Valid @RequestBody AssetDTO assetDTO) {
        Asset updatedAsset = this.assetService.update(assetMapper.toEntity(assetDTO, id));
        return ResponseEntity.ok(this.assetMapper.toDTO(updatedAsset));
    }

    @Operation(summary = "Delete asset", tags = { "Assets" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable UUID id) {
        this.assetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
