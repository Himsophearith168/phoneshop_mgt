package com.example.PhoneShop.DTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testPriceDTO_Validation() {
        PriceDTO invalidPrice = new PriceDTO(null);
        Set<ConstraintViolation<PriceDTO>> violations = validator.validate(invalidPrice);
        assertThat(violations).isNotEmpty();

        PriceDTO negativePrice = new PriceDTO(new BigDecimal("-5.00"));
        violations = validator.validate(negativePrice);
        assertThat(violations).isNotEmpty();

        PriceDTO validPrice = new PriceDTO(new BigDecimal("199.99"));
        violations = validator.validate(validPrice);
        assertThat(violations).isEmpty();
    }

    @Test
    void testProductImportDTO_Validation() {
        ProductImportDTO nullFields = new ProductImportDTO();
        Set<ConstraintViolation<ProductImportDTO>> violations = validator.validate(nullFields);
        assertThat(violations).hasSize(4);

        ProductImportDTO valid = ProductImportDTO.builder()
                .product_id(1L).importUnit(10)
                .importPrice(new BigDecimal("500.00")).importDate(LocalDateTime.now()).build();
        violations = validator.validate(valid);
        assertThat(violations).isEmpty();
    }

    @Test
    void testSaleDTO_Validation() {
        SaleDTO emptySale = new SaleDTO();
        Set<ConstraintViolation<SaleDTO>> violations = validator.validate(emptySale);
        assertThat(violations).isNotEmpty();

        ProductSoldDTO invalidSold = new ProductSoldDTO(null, 0);
        SaleDTO saleWithInvalidChild = SaleDTO.builder()
                .products(List.of(invalidSold)).saleDate(LocalDateTime.now()).build();
        violations = validator.validate(saleWithInvalidChild);
        assertThat(violations).isNotEmpty();

        ProductSoldDTO validSold = new ProductSoldDTO(1L, 2);
        SaleDTO validSale = SaleDTO.builder()
                .products(List.of(validSold)).saleDate(LocalDateTime.now()).build();
        violations = validator.validate(validSale);
        assertThat(violations).isEmpty();
    }
}