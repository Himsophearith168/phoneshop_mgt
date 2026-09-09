package com.example.PhoneShop.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {
    @NotNull(message = "Products list cannot be null")
    @NotEmpty(message = "Products list cannot be empty")
    @Valid
    private List<ProductSoldDTO> products;

    @NotNull(message = "Sale date cannot be null")
    private LocalDateTime saleDate;
}
