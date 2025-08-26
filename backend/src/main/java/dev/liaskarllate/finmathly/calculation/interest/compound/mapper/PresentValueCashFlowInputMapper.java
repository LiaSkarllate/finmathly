package dev.liaskarllate.finmathly.calculation.interest.compound.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.calculation.interest.compound.dto.PresentValueCashFlowInputDTO;
import dev.liaskarllate.finmathly.calculation.interest.compound.model.PresentValueCashFlowInput;

@Mapper(componentModel = "spring")
public interface PresentValueCashFlowInputMapper {
    PresentValueCashFlowInputDTO toDTO(PresentValueCashFlowInput entity);

    PresentValueCashFlowInput toModel(PresentValueCashFlowInputDTO dto);
}
