package dev.liaskarllate.finmathly.marketindex.controller;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.marketindex.mapper.MarketIndexMapper;
import dev.liaskarllate.finmathly.marketindex.query.MarketIndexSearchFilter;
import dev.liaskarllate.finmathly.marketindex.dto.MarketIndexDTO;
import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;
import dev.liaskarllate.finmathly.marketindex.service.MarketIndexService;
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
@RequestMapping("/market-indexes")
@Tag(name = "Market Indexes", description = "Service responsible for managing market indexes.")
public class MarketIndexController {
    private final MarketIndexService marketIndexService;
    private final MarketIndexMapper marketIndexMapper;

    @Operation(summary = "List market indexes", tags = { "Market Indexes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns a list of market indexes.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MarketIndexDTO.class)))),
            @ApiResponse(responseCode = "204", description = "No content. There are no market indexes to return.")
    })
    @GetMapping
    public ResponseEntity<Page<MarketIndexDTO>> listMarketIndexes(
            @Valid @ModelAttribute MarketIndexSearchFilter filter,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<MarketIndexDTO> marketIndexes = this.marketIndexService.findByFilter(filter, pageable)
                .map(this.marketIndexMapper::toDTO);
        return ResponseEntity.ok(marketIndexes);
    }

    @Operation(summary = "Get market index", tags = { "Market Indexes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the market index.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketIndexDTO.class))),
            @ApiResponse(responseCode = "204", description = "No content. No market index found for the given id to return."),
            @ApiResponse(responseCode = "400", description = "Bad request.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MarketIndexDTO> getMarketIndex(@PathVariable UUID id) {
        MarketIndex marketIndex = this.marketIndexService.findById(id);
        return ResponseEntity.ok(this.marketIndexMapper.toDTO(marketIndex));
    }

    @Operation(summary = "Create market index", tags = { "Market Indexes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created. Returns the created market index.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketIndexDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @PostMapping
    public ResponseEntity<MarketIndexDTO> createMarketIndex(@Valid @RequestBody MarketIndexDTO marketIndexDTO) {
        MarketIndex createdMarketIndex = this.marketIndexService.save(this.marketIndexMapper.toEntity(marketIndexDTO));
        return new ResponseEntity<>(this.marketIndexMapper.toDTO(createdMarketIndex), HttpStatus.CREATED);
    }

    @Operation(summary = "Update market index", tags = { "Market Indexes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the updated market index.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketIndexDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MarketIndexDTO> updateMarketIndex(@PathVariable UUID id,
            @Valid @RequestBody MarketIndexDTO marketIndexDTO) {
        MarketIndex updatedMarketIndex = this.marketIndexService
                .update(this.marketIndexMapper.toEntity(marketIndexDTO, id));
        return ResponseEntity.ok(this.marketIndexMapper.toDTO(updatedMarketIndex));
    }

    @Operation(summary = "Delete market index", tags = { "Market Indexes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarketIndexById(@PathVariable UUID id) {
        this.marketIndexService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
