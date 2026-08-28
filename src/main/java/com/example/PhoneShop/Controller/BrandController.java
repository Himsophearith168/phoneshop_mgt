package com.example.PhoneShop.Controller;

import com.example.PhoneShop.DTO.BrandRequest;
import com.example.PhoneShop.DTO.BrandResponse;
import com.example.PhoneShop.DTO.PageDTO;
import com.example.PhoneShop.Entity.BrandEntity;
import com.example.PhoneShop.Service.BrandService;
import com.example.PhoneShop.Util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<APIResponse<BrandResponse>> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse response = brandService.createBrand(brandRequest);
        APIResponse<BrandResponse> apiResponse = APIResponse.<BrandResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Brand created successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageDTO>> getBrands(@RequestParam(required = false) Map<String, String> params) {
        Page<BrandEntity> page = brandService.getBrands(params);
        PageDTO pageDTO = new PageDTO(page);
        APIResponse<PageDTO> apiResponse = APIResponse.<PageDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Brands retrieved successfully")
                .data(pageDTO)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<BrandResponse>> getBrandById(@PathVariable("id") Long id) {
        BrandResponse brandResponse = brandService.getBrand(id);
        APIResponse<BrandResponse> apiResponse = APIResponse.<BrandResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Brand retrieved successfully")
                .data(brandResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<BrandResponse>> updateBrand(
            @PathVariable("id") Long id,
            @Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.updateBrand(id, brandRequest);
        APIResponse<BrandResponse> apiResponse = APIResponse.<BrandResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Brand updated successfully")
                .data(brandResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteBrand(@PathVariable("id") Long id) {
        brandService.deleteBrand(id);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Brand deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
