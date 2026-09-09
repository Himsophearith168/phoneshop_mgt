package com.example.PhoneShop.Mapper;

import com.example.PhoneShop.DTO.ColorRequest;
import com.example.PhoneShop.DTO.ColorResponse;
import com.example.PhoneShop.Entity.ColorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    @Mapping(target = "id", ignore = true)
    ColorEntity toEntity(ColorRequest request);

    ColorResponse toResponse(ColorEntity entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(ColorRequest request, @MappingTarget ColorEntity entity);
}
