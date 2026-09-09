package com.example.PhoneShop.Mapper;

import com.example.PhoneShop.DTO.ProductResponse;
import com.example.PhoneShop.Entity.ColorEntity;
import com.example.PhoneShop.Entity.ModelEntity;
import com.example.PhoneShop.Entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "model", source = "model")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "productName", expression = "java(model.getModelName() + \" \" + color.getColorName())")
    @Mapping(target = "availableUnit", constant = "0")
    @Mapping(target = "salePrice", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "imagePath", ignore = true)
    ProductEntity toEntity(ModelEntity model, ColorEntity color);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "model.modelName", target = "modelName")
    @Mapping(source = "color.id", target = "colorId")
    @Mapping(source = "color.colorName", target = "colorName")
    @Mapping(source = "salePrice", target = "salePrice")
    @Mapping(source = "availableUnit", target = "availableUnit")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "imagePath", target = "imagePath")
    ProductResponse toResponse(ProductEntity entity);
}
