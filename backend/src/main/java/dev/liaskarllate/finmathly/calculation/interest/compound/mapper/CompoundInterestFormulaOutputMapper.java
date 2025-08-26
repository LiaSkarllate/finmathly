package dev.liaskarllate.finmathly.calculation.interest.compound.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.calculation.interest.compound.dto.CompoundInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.compound.model.CompoundInterestFormulaOutput;

@Mapper(componentModel = "spring")
public interface CompoundInterestFormulaOutputMapper {
    CompoundInterestFormulaOutputDTO toDTO(CompoundInterestFormulaOutput entity);

    CompoundInterestFormulaOutput toModel(CompoundInterestFormulaOutputDTO dto);
}
