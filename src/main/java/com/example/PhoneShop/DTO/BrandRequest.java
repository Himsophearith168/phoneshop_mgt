package com.example.PhoneShop.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequest {
    @NotBlank(message = "Brand name cannot be blank")
    private String brandName;
    private String description;
}

