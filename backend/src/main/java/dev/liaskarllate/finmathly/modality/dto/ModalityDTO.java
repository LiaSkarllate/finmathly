package dev.liaskarllate.finmathly.modality.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.enums.YieldType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ModalityDTO {
    private UUID id;

    @NotBlank(message = "The modality name is required. Please, provide a name.")
    @Size(max = 25, message = "The modality name cannot exceed 25 characters. Please, provide a shorter name.")
    private String name;

    @NotNull(message = "The modality yield type is required. Please, provide a yield type.")
    private YieldType yieldType;

    @NotNull(message = "The modality capitalization period is required. Please, provide a capitalization period.")
    private CapitalizationPeriod capitalizationPeriod;

    private Boolean supportsFlows = false;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
