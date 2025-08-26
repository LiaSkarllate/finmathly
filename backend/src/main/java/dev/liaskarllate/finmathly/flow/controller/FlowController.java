package dev.liaskarllate.finmathly.flow.controller;

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

import dev.liaskarllate.finmathly.flow.mapper.FlowMapper;
import dev.liaskarllate.finmathly.flow.query.FlowSearchFilter;
import dev.liaskarllate.finmathly.flow.dto.FlowDTO;
import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.service.FlowService;
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
@RequestMapping("/flows")
@Tag(name = "Flows", description = "Service responsible for managing flows.")
public class FlowController {
    private final FlowService flowService;
    private final FlowMapper flowMapper;

    @Operation(summary = "List flows", tags = { "Flows" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns a list of flows.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FlowDTO.class)))),
            @ApiResponse(responseCode = "204", description = "No content. There are no flows to return.")
    })
    @GetMapping
    public ResponseEntity<Page<FlowDTO>> listFlows(
            @Valid @ModelAttribute FlowSearchFilter filter,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<FlowDTO> flows = this.flowService
                .findByFilter(filter, pageable)
                .map(this.flowMapper::toDTO);
        return ResponseEntity.ok(flows);
    }

    @Operation(summary = "Get flow", tags = { "Flows" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the flow.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FlowDTO.class))),
            @ApiResponse(responseCode = "204", description = "No content. No flow found for the given id to return."),
            @ApiResponse(responseCode = "400", description = "Bad request.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FlowDTO> getFlow(@PathVariable UUID id) {
        Flow flow = this.flowService.findById(id);
        return ResponseEntity.ok(this.flowMapper.toDTO(flow));
    }

    @Operation(summary = "Create flow", tags = { "Flows" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created. Returns the created flow.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FlowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @PostMapping
    public ResponseEntity<FlowDTO> createFlow(@Valid @RequestBody FlowDTO flowDTO) {
        Flow createdFlow = this.flowService.save(this.flowMapper.toEntity(flowDTO));
        return new ResponseEntity<>(this.flowMapper.toDTO(createdFlow), HttpStatus.CREATED);
    }

    @Operation(summary = "Update flow", tags = { "Flows" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the updated flow.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FlowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<FlowDTO> updateFlow(@PathVariable UUID id, @Valid @RequestBody FlowDTO flowDTO) {
        Flow updatedFlow = this.flowService.update(flowMapper.toEntity(flowDTO, id));
        return ResponseEntity.ok(this.flowMapper.toDTO(updatedFlow));
    }

    @Operation(summary = "Delete flow", tags = { "Flows" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "412", description = "Precondition failed.", content = @Content(mediaType = "application/json", schema = @Schema(name = "Error", implementation = ThrownExceptionDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlow(@PathVariable UUID id) {
        this.flowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
