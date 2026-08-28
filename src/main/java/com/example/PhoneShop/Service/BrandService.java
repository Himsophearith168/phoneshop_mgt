package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.BrandRequest;
import com.example.PhoneShop.DTO.BrandResponse;
import com.example.PhoneShop.Entity.BrandEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BrandService {
    BrandResponse createBrand(BrandRequest brandRequest);
    BrandResponse updateBrand(Long brandId, BrandRequest brandRequest);
    void deleteBrand(Long brandId);
    BrandResponse getBrand(Long brandId);
    List<BrandResponse> getBrands();
    Page<BrandEntity> getBrands(Map<String, String> params);
}
