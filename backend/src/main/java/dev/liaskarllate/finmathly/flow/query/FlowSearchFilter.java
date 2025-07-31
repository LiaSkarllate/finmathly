package dev.liaskarllate.finmathly.flow.query;

import java.time.LocalDate;
import java.util.UUID;

import dev.liaskarllate.finmathly.shared.enums.EventType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FlowSearchFilter {
    @Size(max = 25, message = "The asset name cannot exceed 25 characters. Please, provide a shorter name.")
    private String assetName;
    private UUID assetID;
    private EventType type;
    private LocalDate eventDate;
}
