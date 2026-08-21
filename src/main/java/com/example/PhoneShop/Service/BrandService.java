package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.BrandRequest;
import com.example.PhoneShop.DTO.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse createBrand(BrandRequest brandRequest);
    BrandResponse updateBrand(BrandRequest brandRequest);
    BrandResponse deleteBrand(Integer brandId);
    BrandResponse getBrand(Integer brandId);
    List<BrandResponse> getBrands();
}
