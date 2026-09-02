package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.ModelRequest;
import com.example.PhoneShop.DTO.ModelResponse;
import com.example.PhoneShop.Entity.ModelEntity;

import java.util.List;

public interface ModelService {
    ModelResponse createModel(ModelRequest request);
    List<ModelEntity> getByBrand(Long brandId);
}
