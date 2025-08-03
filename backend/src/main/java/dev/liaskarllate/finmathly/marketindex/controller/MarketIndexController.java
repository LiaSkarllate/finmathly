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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/market-indexs")
public class MarketIndexController {
    private final MarketIndexService marketIndexService;
    private final MarketIndexMapper marketIndexMapper;

    @GetMapping
    public ResponseEntity<Page<MarketIndexDTO>> getMarketIndexs(
            @Valid @ModelAttribute MarketIndexSearchFilter filter,
            @PageableDefault(page = 0, size = 5)
            Pageable pageable) {
        Page<MarketIndexDTO> marketIndexs = this.marketIndexService.findByFilter(filter, pageable)
                .map(this.marketIndexMapper::toDTO);
        return ResponseEntity.ok(marketIndexs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarketIndexDTO> getMarketIndexById(@PathVariable UUID id) {
        MarketIndex marketIndex = this.marketIndexService.findById(id);
        return ResponseEntity.ok(this.marketIndexMapper.toDTO(marketIndex));
    }

    @PostMapping
    public ResponseEntity<MarketIndexDTO> saveMarketIndex(@Valid @RequestBody MarketIndexDTO marketIndexDTO) {
        MarketIndex createdMarketIndex = this.marketIndexService.save(this.marketIndexMapper.toEntity(marketIndexDTO));
        return new ResponseEntity<>(this.marketIndexMapper.toDTO(createdMarketIndex), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarketIndexDTO> updateMarketIndex(@PathVariable UUID id, @Valid @RequestBody MarketIndexDTO marketIndexDTO) {
        MarketIndex updatedMarketIndex = this.marketIndexService.update(this.marketIndexMapper.toEntity(marketIndexDTO, id));
        return ResponseEntity.ok(this.marketIndexMapper.toDTO(updatedMarketIndex));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarketIndexById(@PathVariable UUID id) {
        this.marketIndexService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
