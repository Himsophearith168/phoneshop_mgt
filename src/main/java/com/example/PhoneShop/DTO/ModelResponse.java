package com.example.PhoneShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelResponse {
    private Long modelId;
    private String modelName;
    private String modelDescription;
    private Long brandId;

}
