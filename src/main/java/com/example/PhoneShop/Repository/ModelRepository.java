package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    List<ModelEntity> findByBrandId(Long id);
    List<ModelEntity> findByBrandIdAndModelNameStartingWith(Long brandId, String modelName);
}
