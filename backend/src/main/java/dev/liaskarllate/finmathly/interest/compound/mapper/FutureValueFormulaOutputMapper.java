package dev.liaskarllate.finmathly.interest.compound.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.interest.compound.dto.FutureValueFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.compound.model.FutureValueFormulaOutput;

@Mapper(componentModel = "spring")
public interface FutureValueFormulaOutputMapper {
    FutureValueFormulaOutputDTO toDTO(FutureValueFormulaOutput entity);

    FutureValueFormulaOutput toModel(FutureValueFormulaOutputDTO dto);
}
