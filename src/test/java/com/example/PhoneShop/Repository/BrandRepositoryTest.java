package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.BrandEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void testSaveAndFindBrand() {
        BrandEntity brand = BrandEntity.builder()
                .name("Apple")
                .description("Apple Inc.")
                .build();

        BrandEntity savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isNotNull();

        Optional<BrandEntity> foundBrand = brandRepository.findById(savedBrand.getId());
        assertThat(foundBrand).isPresent();
        assertThat(foundBrand.get().getName()).isEqualTo("Apple");
    }

    @Test
    void testFindByNameLike() {
        BrandEntity brand = BrandEntity.builder()
                .name("Samsung")
                .description("Samsung Electronics")
                .build();
        brandRepository.save(brand);

        var results = brandRepository.findByNameLike("%msun%");
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getName()).isEqualTo("Samsung");
    }

    @Test
    void testFindByNameContaining() {
        BrandEntity brand = BrandEntity.builder()
                .name("Xiaomi")
                .description("Xiaomi Inc.")
                .build();
        brandRepository.save(brand);

        var results = brandRepository.findByNameContaining("aom");
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getName()).isEqualTo("Xiaomi");
    }
}

