package com.example.PhoneShop.Service.Impl;

import com.example.PhoneShop.DTO.BrandRequest;
import com.example.PhoneShop.DTO.BrandResponse;
import com.example.PhoneShop.Entity.BrandEntity;
import com.example.PhoneShop.Exception.ResourceNotFoundException;
import com.example.PhoneShop.Mapper.BrandMapper;
import com.example.PhoneShop.Repository.BrandRepository;
import com.example.PhoneShop.Service.BrandService;
import com.example.PhoneShop.Spec.BrandFilter;
import com.example.PhoneShop.Spec.BrandSpec;
import com.example.PhoneShop.Util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        BrandEntity brandEntity = brandMapper.toEntity(brandRequest);
        BrandEntity savedBrand = brandRepository.save(brandEntity);
        return brandMapper.toResponse(savedBrand);
    }

    @Override
    public BrandResponse updateBrand(Long brandId, BrandRequest brandRequest) {
        BrandEntity brandEntity = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", brandId));
        brandMapper.updateEntityFromRequest(brandRequest, brandEntity);
        BrandEntity updatedBrand = brandRepository.save(brandEntity);
        return brandMapper.toResponse(updatedBrand);
    }

    @Override
    public void deleteBrand(Long brandId) {
        BrandEntity brandEntity = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", brandId));
        brandRepository.delete(brandEntity);
    }

    @Override
    public BrandResponse getBrand(Long brandId) {
        BrandEntity brandEntity = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", brandId));
        return brandMapper.toResponse(brandEntity);
    }

    @Override
    public List<BrandResponse> getBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BrandEntity> getBrands(Map<String, String> params) {
        BrandFilter brandFilter = BrandFilter.fromParams(params);
        BrandSpec brandSpec = new BrandSpec(brandFilter);
        Pageable pageable = PageUtil.getPageable(params);
        return brandRepository.findAll(brandSpec, pageable);
    }
}
