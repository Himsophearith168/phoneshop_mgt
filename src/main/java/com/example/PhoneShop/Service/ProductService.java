package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.ProductImportDTO;
import com.example.PhoneShop.DTO.ProductRequest;
import com.example.PhoneShop.DTO.ProductResponse;
import com.example.PhoneShop.Entity.ProductEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductEntity getProductById(Long id);
    ProductResponse getProduct(Long id);
    List<ProductResponse> getProducts();
    ProductResponse setSalePrice(Long id, BigDecimal price);
    ProductResponse importProduct(ProductImportDTO importDTO);
}
