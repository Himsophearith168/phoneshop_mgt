package com.example.PhoneShop.Service.Impl;

import com.example.PhoneShop.DTO.ModelRequest;
import com.example.PhoneShop.DTO.ModelResponse;
import com.example.PhoneShop.Entity.BrandEntity;
import com.example.PhoneShop.Entity.ModelEntity;
import com.example.PhoneShop.Mapper.ModelMapper;
import com.example.PhoneShop.Repository.ModelRepository;
import com.example.PhoneShop.Service.BrandService;
import com.example.PhoneShop.Service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    private final BrandService brandService;

    @Override
    public ModelResponse createModel(ModelRequest request) {
        BrandEntity brandEntity = brandService.getById(request.getBrandId());
        ModelEntity modelEntity = modelMapper.toEntity(request);
        modelEntity.setBrand(brandEntity);
        ModelEntity savedModel = modelRepository.save(modelEntity);
        return modelMapper.toResponse(savedModel);
    }

    @Override
    public List<ModelEntity> getByBrand(Long brandId) {
        return modelRepository.findByBrandId(brandId);
    }
}
