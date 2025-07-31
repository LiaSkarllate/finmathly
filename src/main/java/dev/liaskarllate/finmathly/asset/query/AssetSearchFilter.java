package dev.liaskarllate.finmathly.asset.query;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AssetSearchFilter {
    @Size(max = 25, message = "The asset name cannot exceed 25 characters. Please, provide a shorter name.")
    private String name;

    private UUID modalityId;
    
    @Size(max = 25, message = "The asset modality name cannot exceed 25 characters. Please, provide a shorter name.")
    private String modalityName;

    private LocalDate maturityDate;

    private UUID marketIndexId;

    @Size(max = 25, message = "The asset market index name cannot exceed 25 characters. Please, provide a shorter name.")
    private String marketIndexName; 
}
