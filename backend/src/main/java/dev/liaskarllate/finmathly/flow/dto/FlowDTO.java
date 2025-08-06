package dev.liaskarllate.finmathly.flow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import dev.liaskarllate.finmathly.shared.enums.EventType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowDTO {
    private UUID id;

    private UUID assetId;

    @NotNull(message = "The flow asset name is required. Please, provide a flow asset name.")
    @Size(max = 25, message = "The asset name cannot exceed 25 characters. Please, provide a shorter name.")
    private String assetName;

    @NotNull(message = "The flow event type is required, Please, provide a event type.")
    private EventType type;

    @NotNull(message = "The flow event date is required. Please, provide a event date.")
    private LocalDate eventDate;

    private BigDecimal amount;
    private BigDecimal amortizationPercentage;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
