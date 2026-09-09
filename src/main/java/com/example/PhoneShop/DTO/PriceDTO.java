package com.example.PhoneShop.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceDTO {
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.000001", message = "Price must be greater than 0")
    private BigDecimal price;
}

