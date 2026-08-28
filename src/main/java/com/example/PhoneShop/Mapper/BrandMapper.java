package com.example.PhoneShop.Mapper;

import com.example.PhoneShop.DTO.BrandRequest;
import com.example.PhoneShop.DTO.BrandResponse;
import com.example.PhoneShop.Entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(source = "brandName", target = "name")
    @Mapping(target = "id", ignore = true)
    BrandEntity toEntity(BrandRequest request);

    @Mapping(source = "name", target = "brandName")
    BrandResponse toResponse(BrandEntity entity);

    @Mapping(source = "brandName", target = "name")
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(BrandRequest request, @MappingTarget BrandEntity entity);
}