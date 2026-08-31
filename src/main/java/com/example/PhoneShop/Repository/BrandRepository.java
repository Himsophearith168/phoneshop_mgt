package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BrandRepository extends JpaRepository<BrandEntity, Long>, JpaSpecificationExecutor<BrandEntity> {
    List<BrandEntity> findByNameLike(String name);
    List<BrandEntity> findByNameContaining(String name);
}
