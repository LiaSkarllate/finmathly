package dev.liaskarllate.finmathly.interest.compound.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.interest.compound.dto.CompoundInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.compound.model.CompoundInterestFormulaOutput;

@Mapper(componentModel = "spring")
public interface CompoundInterestFormulaOutputMapper {
    CompoundInterestFormulaOutputDTO toDTO(CompoundInterestFormulaOutput entity);

    CompoundInterestFormulaOutput toModel(CompoundInterestFormulaOutputDTO dto);
}
