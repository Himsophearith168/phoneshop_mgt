package com.example.PhoneShop.Service.Impl;

import com.example.PhoneShop.DTO.BrandRequest;
import com.example.PhoneShop.DTO.BrandResponse;
import com.example.PhoneShop.Mapper.BrandMapper;
import com.example.PhoneShop.Repository.BrandRepository;
import com.example.PhoneShop.Service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        return null;
    }

    @Override
    public BrandResponse updateBrand(BrandRequest brandRequest) {
        return null;
    }

    @Override
    public BrandResponse deleteBrand(Integer brandId) {
        return null;
    }

    @Override
    public BrandResponse getBrand(Integer brandId) {
        return null;
    }

    @Override
    public List<BrandResponse> getBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toResponse)
                .collect(Collectors.toList());
    }
}
