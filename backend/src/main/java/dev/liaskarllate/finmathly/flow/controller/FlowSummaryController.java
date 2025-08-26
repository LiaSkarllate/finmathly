package dev.liaskarllate.finmathly.flow.controller;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.liaskarllate.finmathly.flow.query.FlowSearchFilter;
import dev.liaskarllate.finmathly.flow.query.FlowSumField;
import dev.liaskarllate.finmathly.flow.service.FlowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/flows/summary")
@Tag(name = "Flows summaries", description = "Service responsible for providing flows summaries.")
public class FlowSummaryController {
    private final FlowService flowService;

    @Operation(summary = "Sum amount by filter", tags = { "Flows summaries" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the sum.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class)))
    })
    @GetMapping("/sum-amount")
    public ResponseEntity<BigDecimal> sumAmountByFilter(
            @Valid @ModelAttribute FlowSearchFilter filter) {
        BigDecimal sum = this.flowService.sumAmountByFilter(filter);
        return ResponseEntity.ok(sum);
    }

    @Operation(summary = "Sum by field and filter", tags = { "Flows summaries" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Returns the sum.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class)))
    })
    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> sumByFieldAndFilter(
            @Valid @ModelAttribute FlowSearchFilter filter,
            @RequestParam FlowSumField by) {
        BigDecimal sum = this.flowService.sumByFieldAndFilter(filter, by);
        return ResponseEntity.ok(sum);
    }
}
