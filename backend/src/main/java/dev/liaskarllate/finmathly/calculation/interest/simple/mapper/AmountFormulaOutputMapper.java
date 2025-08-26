package dev.liaskarllate.finmathly.calculation.interest.simple.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.calculation.interest.simple.dto.AmountFormulaOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.simple.model.AmountFormulaOutput;

@Mapper(componentModel = "spring")
public interface AmountFormulaOutputMapper {
    AmountFormulaOutputDTO toDTO(AmountFormulaOutput entity);

    AmountFormulaOutput toModel(AmountFormulaOutputDTO dto);
}
