package com.example.PhoneShop.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {
    @NotNull(message = "Model ID cannot be null")
    private Long models_id;

    @NotNull(message = "Color ID cannot be null")
    private Long color_id;
}
