package dev.liaskarllate.finmathly.flow.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import dev.liaskarllate.finmathly.flow.entity.Flow;

public interface FlowRepository extends
        JpaRepository<Flow, UUID>,
        JpaSpecificationExecutor<Flow>,
        FlowSummaryRepository {
}
