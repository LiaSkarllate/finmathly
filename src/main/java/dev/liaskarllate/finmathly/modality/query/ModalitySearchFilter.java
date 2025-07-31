package dev.liaskarllate.finmathly.modality.query;

import dev.liaskarllate.finmathly.shared.enums.YieldType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ModalitySearchFilter {
    @Size(max = 25, message = "The modality name cannot exceed 25 characters. Please, provide a shorter name.")
    private String name;
    private YieldType yieldType;
}
