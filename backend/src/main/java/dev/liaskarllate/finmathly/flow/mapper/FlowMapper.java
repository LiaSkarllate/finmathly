package dev.liaskarllate.finmathly.flow.mapper;

import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.dto.FlowDTO;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlowMapper {
    @Mapping(target = "assetId", source = "asset.id")
    @Mapping(target = "assetName", source = "asset.name")
    FlowDTO toDTO(Flow entity);

    @Mapping(target = "asset", expression = "java(new Asset(dto.getAssetId(), dto.getAssetName()))")
    Flow toEntity(FlowDTO dto);

    @Mapping(target = "asset", expression = "java(new Asset(dto.getAssetId(), dto.getAssetName()))") 
    @Mapping(target = "id", source = "id")
    Flow toEntity(FlowDTO dto, UUID id);
}
