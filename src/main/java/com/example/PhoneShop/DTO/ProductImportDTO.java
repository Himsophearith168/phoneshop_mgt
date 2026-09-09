package com.example.PhoneShop.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductImportDTO {
    @NotNull(message = "Product id can't be null")
    private Long product_id;

    @Min(value = 1, message = "import unit must be greater than 0")
    private Integer importUnit;

    @DecimalMin(value = "0.000001", message = "Price must be greater than 0")
    private BigDecimal importPrice;

    @NotNull(message = "Import date can't be null")
    private LocalDateTime importDate;
}
