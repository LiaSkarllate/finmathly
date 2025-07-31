package dev.liaskarllate.finmathly.asset.mapper;

import dev.liaskarllate.finmathly.asset.dto.AssetDTO;
import dev.liaskarllate.finmathly.asset.entity.Asset;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    @Mapping(target = "modalityId", source = "modality.id")
    @Mapping(target = "modalityName", source = "modality.name")
    @Mapping(target = "marketIndexId", source = "marketIndex.id")
    @Mapping(target = "marketIndexName", source = "marketIndex.name")
    AssetDTO toDTO(Asset entity);

    @Mapping(target = "modality", expression = "java(new Modality(dto.getModalityId(), dto.getModalityName()))")
    @Mapping(target = "marketIndex", expression = "java(new MarketIndex(dto.getMarketIndexId(), dto.getMarketIndexName()))")
    Asset toEntity(AssetDTO dto);

    @Mapping(target = "modality", expression = "java(new Modality(dto.getModalityId(), dto.getModalityName()))")
    @Mapping(target = "marketIndex", expression = "java(new MarketIndex(dto.getMarketIndexId(), dto.getMarketIndexName()))")
    @Mapping(target = "id", source = "id")
    Asset toEntity(AssetDTO dto, UUID id);
}
