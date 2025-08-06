package dev.liaskarllate.finmathly.marketindex.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketIndexDTO {
    private UUID id;

    @NotBlank(message = "The market index name is required. Please, provide a name.")
    @Size(max = 25, message = "The market index name cannot exceed 25 characters. Please, provide a shorter name.")
    private String name;

    private String description;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}