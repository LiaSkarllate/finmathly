package dev.liaskarllate.finmathly.asset.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AssetDTO {
    private UUID id;

    @NotBlank(message = "The asset name is required. Please, provide a name.")
    @Size(max = 25, message = "The asset name cannot exceed 25 characters. Please, provide a shorter name.")
    private String name;

    private UUID modalityId;

    @Size(max = 25, message = "The asset modality name cannot exceed 25 characters. Please, provide a shorter name.")
    private String modalityName;

    @NotNull(message = "The asset maturity date is required. Please, provide a maturity date.")
    private LocalDate maturityDate;

    private BigDecimal interestRate;

    private UUID marketIndexId;

    @Size(max = 25, message = "The asset market index name cannot exceed 25 characters. Please, provide a shorter name.")
    private String marketIndexName;

    private BigDecimal indexPercentage;
    private BigDecimal faceValue;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
