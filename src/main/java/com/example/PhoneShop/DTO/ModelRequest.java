package com.example.PhoneShop.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelRequest {
    @NotBlank(message = "Model Name Need To Input")
    private String modelName;
    private String description;
    @NotNull(message = "Brand ID is required")
    private Long brandId;
}
