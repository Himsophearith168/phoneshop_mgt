package com.example.PhoneShop.Mapper;

import com.example.PhoneShop.DTO.BrandRequest;
import com.example.PhoneShop.DTO.BrandResponse;
import com.example.PhoneShop.DTO.ModelRequest;
import com.example.PhoneShop.DTO.ModelResponse;
import com.example.PhoneShop.Entity.BrandEntity;
import com.example.PhoneShop.Entity.ModelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", ignore = true)
    ModelEntity toEntity(ModelRequest request);

    @Mapping(source = "id", target = "modelId")
    @Mapping(source = "description", target = "modelDescription")
    @Mapping(source = "brand.id", target = "brandId")
    ModelResponse toResponse(ModelEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", ignore = true)
    void updateEntityFromRequest(ModelRequest request, @MappingTarget ModelEntity entity);
}
