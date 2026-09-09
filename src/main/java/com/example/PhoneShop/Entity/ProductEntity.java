package com.example.PhoneShop.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(unique = true)
    private String productName;

    private String description;
    @Column(name = "Image_Path")
    private String imagePath;
    @Column(name = "available_Unit")
    private Integer availableUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "models_id")
    private ModelEntity model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private ColorEntity color;

    @DecimalMin(value = "0.000001",message = "Price must be greater than 0")
    @Column(name = "sale_price")
    private BigDecimal salePrice;
}
