package dev.liaskarllate.finmathly.calculation.interest.simple.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.calculation.interest.simple.dto.EquivalenceCashFlowInputDTO;
import dev.liaskarllate.finmathly.calculation.interest.simple.model.EquivalenceCashFlowInput;

@Mapper(componentModel = "spring")
public interface EquivalenceCashFlowInputMapper {
    EquivalenceCashFlowInputDTO toDTO(EquivalenceCashFlowInput entity);

    EquivalenceCashFlowInput toModel(EquivalenceCashFlowInputDTO dto);
}
