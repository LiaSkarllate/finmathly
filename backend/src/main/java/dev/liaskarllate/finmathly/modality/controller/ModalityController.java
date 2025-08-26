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
@RequestMapping("/modalities")
@Tag(name = "Modalities", description = "Service responsible for managing modalities.")
public class ModalityController {
    private final ModalityService modalityService;
    private final ModalityMapper modalityMapper;

    @Operation(summary = "List modalities", tags = { "Modalities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns a list of modalities.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ModalityDTO.class)))),
            @ApiResponse(responseCode = "204", description = "No content. There are no modalities to return.")
    })
    @GetMapping
    public ResponseEntity<Page<ModalityDTO>> listModalities(
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

    @Operation(summary = "Get modality", tags = { "Modalities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the modality.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModalityDTO.class))),
            @ApiResponse(responseCode = "204", description = "No content. No modality found for the given id to return."),
            @ApiResponse(responseCode = "400", description = "Bad request.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ModalityDTO> getModality(@PathVariable UUID id) {
        Modality modality = this.modalityService.findById(id);
        return ResponseEntity.ok(this.modalityMapper.toDTO(modality));
    }

    @Operation(summary = "Create modality", tags = { "Modalities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created. Returns the created modality.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModalityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @PostMapping
    public ResponseEntity<ModalityDTO> createModality(@Valid @RequestBody ModalityDTO modalityDTO) {
        Modality createdModality = this.modalityService.save(this.modalityMapper.toEntity(modalityDTO));
        return new ResponseEntity<>(this.modalityMapper.toDTO(createdModality), HttpStatus.CREATED);
    }

    @Operation(summary = "Update modality", tags = { "Modalities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the updated modality.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModalityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ModalityDTO> updateModality(@PathVariable UUID id,
            @Valid @RequestBody ModalityDTO modalityDTO) {
        Modality updatedModality = this.modalityService.update(this.modalityMapper.toEntity(modalityDTO, id));
        return ResponseEntity.ok(this.modalityMapper.toDTO(updatedModality));
    }

    @Operation(summary = "Delete modality", tags = { "Modalities" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModality(@PathVariable UUID id) {
        this.modalityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}