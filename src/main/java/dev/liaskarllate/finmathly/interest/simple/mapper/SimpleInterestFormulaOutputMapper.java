package dev.liaskarllate.finmathly.interest.simple.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.interest.simple.dto.SimpleInterestFormulaOutputDTO;
import dev.liaskarllate.finmathly.interest.simple.model.SimpleInterestFormulaOutput;

@Mapper(componentModel = "spring")
public interface SimpleInterestFormulaOutputMapper {
    SimpleInterestFormulaOutputDTO toDTO(SimpleInterestFormulaOutput entity);
    SimpleInterestFormulaOutput toModel(SimpleInterestFormulaOutputDTO dto);
}
