package dev.liaskarllate.finmathly.calculation.interest.simple.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.calculation.interest.simple.dto.SimpleInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.calculation.interest.simple.model.SimpleInterestFormulaOutput;

@Mapper(componentModel = "spring")
public interface SimpleInterestFormulaOutputMapper {
    SimpleInterestFormulaOutputDTO toDTO(SimpleInterestFormulaOutput entity);

    SimpleInterestFormulaOutput toModel(SimpleInterestFormulaOutputDTO dto);
}
