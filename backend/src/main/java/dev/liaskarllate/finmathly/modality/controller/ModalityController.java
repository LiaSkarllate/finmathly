package dev.liaskarllate.finmathly.modality.controller;

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

import dev.liaskarllate.finmathly.modality.mapper.ModalityMapper;
import dev.liaskarllate.finmathly.modality.query.ModalitySearchFilter;
import dev.liaskarllate.finmathly.modality.query.ModalitySearchSortingField;
import dev.liaskarllate.finmathly.modality.dto.ModalityDTO;
import dev.liaskarllate.finmathly.modality.entity.Modality;
import dev.liaskarllate.finmathly.modality.service.ModalityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/modalities")
public class ModalityController {
    private final ModalityService modalityService;
    private final ModalityMapper modalityMapper;

    @GetMapping
    public ResponseEntity<Page<ModalityDTO>> getModalities(
            @Valid @ModelAttribute ModalitySearchFilter filter,
            @RequestParam Optional<ModalitySearchSortingField> by,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                by.map(field -> Sort.by(Sort.Direction.DESC, field.toString()))
                        .orElse(Sort.unsorted()));

        Page<ModalityDTO> modalities = this.modalityService.findByFilter(filter, pageable)
                .map(this.modalityMapper::toDTO);
        return ResponseEntity.ok(modalities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModalityDTO> getModalityById(@PathVariable UUID id) {
        Modality modality = this.modalityService.findById(id);
        return ResponseEntity.ok(this.modalityMapper.toDTO(modality));
    }

    @PostMapping
    public ResponseEntity<ModalityDTO> saveModality(@Valid @RequestBody ModalityDTO modalityDTO) {
        Modality createdModality = this.modalityService.save(this.modalityMapper.toEntity(modalityDTO));
        return new ResponseEntity<>(this.modalityMapper.toDTO(createdModality), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModalityDTO> updateModality(@PathVariable UUID id,
            @Valid @RequestBody ModalityDTO modalityDTO) {
        Modality updatedModality = this.modalityService.update(this.modalityMapper.toEntity(modalityDTO, id));
        return ResponseEntity.ok(this.modalityMapper.toDTO(updatedModality));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModalityById(@PathVariable UUID id) {
        this.modalityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}