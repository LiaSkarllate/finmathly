package dev.liaskarllate.finmathly.interest.compound.mapper;

import org.mapstruct.Mapper;

import dev.liaskarllate.finmathly.interest.compound.dto.PresentValueCashFlowInputDTO;
import dev.liaskarllate.finmathly.interest.compound.model.PresentValueCashFlowInput;

@Mapper(componentModel = "spring")
public interface PresentValueCashFlowInputMapper {
    PresentValueCashFlowInputDTO toDTO(PresentValueCashFlowInput entity);
    PresentValueCashFlowInput toModel(PresentValueCashFlowInputDTO dto);
}
