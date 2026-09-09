package com.example.PhoneShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private Long modelId;
    private String modelName;
    private Long colorId;
    private String colorName;
    private BigDecimal salePrice;
    private Integer availableUnit;
    private String description;
    private String imagePath;
}
