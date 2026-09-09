package com.example.PhoneShop.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImportDTO {
    @NotNull(message = "Product id can't be null")
    private Long product_id;

    @NotNull(message = "Import unit can't be null")
    @Min(value = 1, message = "import unit must be greater than 0")
    private Integer importUnit;

    @NotNull(message = "Import price can't be null")
    @DecimalMin(value = "0.000001", message = "Price must be greater than 0")
    private BigDecimal importPrice;

    @NotNull(message = "Import date can't be null")
    private LocalDateTime importDate;
}
