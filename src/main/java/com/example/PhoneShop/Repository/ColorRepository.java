package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
    Optional<ColorEntity> findByColorName(String colorName);
    boolean existsByColorName(String colorName);
}
