package com.example.PhoneShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponse {
    private Long id;
    private String brandName;
    private String description;
}

