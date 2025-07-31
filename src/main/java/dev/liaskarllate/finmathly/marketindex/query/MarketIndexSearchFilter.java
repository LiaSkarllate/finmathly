package dev.liaskarllate.finmathly.marketindex.query;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MarketIndexSearchFilter {
    @Size(max = 25, message = "The market index name cannot exceed 25 characters. Please, provide a shorter name.")
    private String name;
}
