package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrandRepository extends JpaRepository<BrandEntity, Long>, JpaSpecificationExecutor<BrandEntity> {
}
