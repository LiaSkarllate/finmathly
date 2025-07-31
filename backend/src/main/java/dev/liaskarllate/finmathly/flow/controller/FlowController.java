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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/flows")
public class FlowController {
    private final FlowService flowService;
    private final FlowMapper flowMapper;

    @GetMapping
    public ResponseEntity<Page<FlowDTO>> getFlows(
            @Valid @ModelAttribute FlowSearchFilter filter,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<FlowDTO> flows = this.flowService
                .findByFilter(filter, pageable)
                .map(this.flowMapper::toDTO);
        return ResponseEntity.ok(flows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowDTO> getFlowById(@PathVariable UUID id) {
        Flow flow = this.flowService.findById(id);
        return ResponseEntity.ok(this.flowMapper.toDTO(flow));
    }

    @PostMapping
    public ResponseEntity<FlowDTO> saveFlow(@Valid @RequestBody FlowDTO flowDTO) {
        Flow createdFlow = this.flowService.save(this.flowMapper.toEntity(flowDTO));
        return new ResponseEntity<>(this.flowMapper.toDTO(createdFlow), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlowDTO> updateFlow(@PathVariable UUID id, @Valid @RequestBody FlowDTO flowDTO) {
        Flow updatedFlow = this.flowService.update(flowMapper.toEntity(flowDTO, id));
        return ResponseEntity.ok(this.flowMapper.toDTO(updatedFlow));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlowById(@PathVariable UUID id) {
        this.flowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
