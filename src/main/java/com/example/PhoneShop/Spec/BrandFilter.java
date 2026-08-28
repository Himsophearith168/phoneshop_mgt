package com.example.PhoneShop.Spec;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandFilter {
    private Long id;
    private String name;
    private Long brandId;
    private String brandName;

    public Long getIdValue() {
        if (id != null) return id;
        return brandId;
    }

    public String getNameValue() {
        if (name != null && !name.isBlank()) return name;
        if (brandName != null && !brandName.isBlank()) return brandName;
        return null;
    }

    public static BrandFilter fromParams(Map<String, String> params) {
        BrandFilter filter = new BrandFilter();
        if (params == null) return filter;

        if (params.containsKey("brandId")) {
            try {
                filter.setBrandId(Long.parseLong(params.get("brandId")));
            } catch (NumberFormatException ignored) {}
        } else if (params.containsKey("id")) {
            try {
                filter.setId(Long.parseLong(params.get("id")));
            } catch (NumberFormatException ignored) {}
        }

        if (params.containsKey("brandName")) {
            filter.setBrandName(params.get("brandName"));
        } else if (params.containsKey("name")) {
            filter.setName(params.get("name"));
        }
        return filter;
    }
}
