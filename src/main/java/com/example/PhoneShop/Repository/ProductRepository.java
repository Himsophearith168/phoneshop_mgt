package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
    Optional<ProductEntity> findByModelIdAndColorId(Long modelId, Long colorId);
    boolean existsByModelIdAndColorId(Long modelId, Long colorId);
    Optional<ProductEntity> findByProductName(String productName);
}
