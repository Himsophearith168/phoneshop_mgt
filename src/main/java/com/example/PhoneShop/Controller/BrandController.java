package com.example.PhoneShop.Controller;


import com.example.PhoneShop.DTO.BrandResponse;
import com.example.PhoneShop.Repository.BrandRepository;
import com.example.PhoneShop.Service.BrandService;
import com.example.PhoneShop.Util.APIResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<BrandResponse>>> getBrands() {
        List<BrandResponse> brandResponseList = brandService.getBrands();
        APIResponse<List<BrandResponse>> apiResponse = APIResponse.<List<BrandResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Brands retrieved successfully")
                .data(brandResponseList)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
