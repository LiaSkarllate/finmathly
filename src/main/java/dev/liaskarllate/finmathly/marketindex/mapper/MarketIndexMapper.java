package dev.liaskarllate.finmathly.marketindex.mapper;

import dev.liaskarllate.finmathly.marketindex.dto.MarketIndexDTO;
import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarketIndexMapper {
    MarketIndexDTO toDTO(MarketIndex entity);
    MarketIndex toEntity(MarketIndexDTO dto);

    @Mapping(target = "id", source = "id")
    MarketIndex toEntity(MarketIndexDTO dto, UUID id);
}
