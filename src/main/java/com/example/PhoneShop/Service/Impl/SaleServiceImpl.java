package com.example.PhoneShop.Service.Impl;

import com.example.PhoneShop.DTO.ProductSoldDTO;
import com.example.PhoneShop.DTO.SaleDTO;
import com.example.PhoneShop.Entity.ProductEntity;
import com.example.PhoneShop.Entity.SoldDetailEntity;
import com.example.PhoneShop.Entity.SoldEntity;
import com.example.PhoneShop.Exception.APIException;
import com.example.PhoneShop.Repository.ProductRepository;
import com.example.PhoneShop.Repository.SaleDetailRepository;
import com.example.PhoneShop.Repository.SaleRepository;
import com.example.PhoneShop.Service.ProductService;
import com.example.PhoneShop.Service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    @Override
    public void sell(SaleDTO saleDTO) {

        List<Long> productIds = saleDTO.getProducts().stream()
                .map(ProductSoldDTO::getProduct_id)
                .toList();

        // validate product exists
        productIds.forEach(productService::getProductById);

        List<ProductEntity> products = productRepository.findAllById(productIds);
        Map<Long, ProductEntity> productMap = products.stream()
                .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));

        // validate stock
        saleDTO.getProducts()
                .forEach(ps -> {
                    ProductEntity product = productMap.get(ps.getProduct_id());
                    if (product.getAvailableUnit() < ps.getNumberOfUnit()) {
                        throw new APIException(HttpStatus.BAD_REQUEST, "Product [%s] is not enough in stock".formatted(product.getProductName()));
                    }
                });

        // Sale
        SoldEntity sale = new SoldEntity();
        sale.setSoldDate(saleDTO.getSaleDate());
        saleRepository.save(sale);

        // Sale Detail
        saleDTO.getProducts().forEach(ps -> {
            ProductEntity product = productMap.get(ps.getProduct_id());
            SoldDetailEntity saleDetail = new SoldDetailEntity();
            saleDetail.setAmount(product.getSalePrice());
            saleDetail.setProduct(product);
            saleDetail.setSold(sale);
            saleDetail.setUnit(ps.getNumberOfUnit());
            saleDetailRepository.save(saleDetail);

            // cut stock
            Integer availableUnit = product.getAvailableUnit() - ps.getNumberOfUnit();
            product.setAvailableUnit(availableUnit);
            productRepository.save(product);
        });
    }
}
