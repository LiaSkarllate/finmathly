package dev.liaskarllate.finmathly.modality.mapper;

import dev.liaskarllate.finmathly.modality.dto.ModalityDTO;
import dev.liaskarllate.finmathly.modality.entity.Modality;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModalityMapper {
    ModalityDTO toDTO(Modality entity);

    Modality toEntity(ModalityDTO dto);

    @Mapping(target = "id", source = "id")
    Modality toEntity(ModalityDTO dto, UUID id);
}
