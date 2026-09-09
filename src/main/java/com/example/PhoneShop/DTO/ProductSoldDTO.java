package com.example.PhoneShop.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSoldDTO {
    @NotNull(message = "Product ID cannot be null")
    private Long product_id;

    @NotNull(message = "Number of units cannot be null")
    @Min(value = 1, message = "Number of units must be greater than 0")
    private Integer numberOfUnit;
}
