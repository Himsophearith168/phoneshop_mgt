package com.example.PhoneShop.Service.Impl;

import com.example.PhoneShop.DTO.ProductImportDTO;
import com.example.PhoneShop.DTO.ProductRequest;
import com.example.PhoneShop.DTO.ProductResponse;
import com.example.PhoneShop.Entity.ColorEntity;
import com.example.PhoneShop.Entity.ModelEntity;
import com.example.PhoneShop.Entity.ProductEntity;
import com.example.PhoneShop.Entity.ProductImportHistory;
import com.example.PhoneShop.Exception.APIException;
import com.example.PhoneShop.Exception.ResourceNotFoundException;
import com.example.PhoneShop.Mapper.ProductMapper;
import com.example.PhoneShop.Repository.ProductImportHistoryRepository;
import com.example.PhoneShop.Repository.ProductRepository;
import com.example.PhoneShop.Service.ColorService;
import com.example.PhoneShop.Service.ModelService;
import com.example.PhoneShop.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImportHistoryRepository productImportHistoryRepository;
    private final ModelService modelService;
    private final ColorService colorService;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        ModelEntity model = modelService.getById(request.getModels_id());
        ColorEntity color = colorService.getColorById(request.getColor_id());

        if (productRepository.existsByModelIdAndColorId(model.getId(), color.getId())) {
            throw new APIException(HttpStatus.CONFLICT, "Product with model and color already exists");
        }

        ProductEntity product = productMapper.toEntity(model, color);
        ProductEntity savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public ProductResponse getProduct(Long id) {
        ProductEntity product = getProductById(id);
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse setSalePrice(Long id, BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Price must be greater than 0");
        }
        ProductEntity product = getProductById(id);
        product.setSalePrice(price);
        ProductEntity savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse importProduct(ProductImportDTO importDTO) {
        if (importDTO.getImportUnit() == null || importDTO.getImportUnit() <= 0) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Import unit must be greater than 0");
        }
        if (importDTO.getImportPrice() == null || importDTO.getImportPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Price must be greater than 0");
        }
        ProductEntity product = getProductById(importDTO.getProduct_id());
        int currentUnit = product.getAvailableUnit() == null ? 0 : product.getAvailableUnit();
        product.setAvailableUnit(currentUnit + importDTO.getImportUnit());
        ProductEntity updatedProduct = productRepository.save(product);

        ProductImportHistory history = new ProductImportHistory();
        history.setProduct(product);
        history.setImportUnit(importDTO.getImportUnit());
        history.setPricePerUnit(importDTO.getImportPrice());
        history.setDateTime(importDTO.getImportDate());
        productImportHistoryRepository.save(history);

        return productMapper.toResponse(updatedProduct);
    }
}
