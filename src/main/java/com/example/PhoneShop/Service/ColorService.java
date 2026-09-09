package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.ColorRequest;
import com.example.PhoneShop.DTO.ColorResponse;
import com.example.PhoneShop.Entity.ColorEntity;

import java.util.List;

public interface ColorService {
    ColorResponse createColor(ColorRequest colorRequest);
    ColorEntity createColor(ColorEntity color);
    ColorResponse updateColor(Long id, ColorRequest colorRequest);
    void deleteColor(Long id);
    ColorResponse getColor(Long id);
    ColorEntity getColorById(Long id);
    List<ColorResponse> getColors();
}
