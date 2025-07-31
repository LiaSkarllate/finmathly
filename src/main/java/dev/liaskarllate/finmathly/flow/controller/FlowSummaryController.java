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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/flows/summary")
public class FlowSummaryController {
    private final FlowService flowService;

    @GetMapping("/sum-amount")
    public ResponseEntity<BigDecimal> sumAmountByFilter(
            @Valid @ModelAttribute FlowSearchFilter filter) {
        BigDecimal sum = this.flowService.sumAmountByFilter(filter);
        return ResponseEntity.ok(sum);
    }

    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> sumByFieldAndFilter(
            @Valid @ModelAttribute FlowSearchFilter filter,
            @RequestParam FlowSumField by){
        BigDecimal sum = this.flowService.sumByFieldAndFilter(filter, by);
        return ResponseEntity.ok(sum);
    }
}
