package com.example.PhoneShop.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColorRequest {
    @NotBlank(message = "The Color Name is Require!!")
    private String colorName;
}
