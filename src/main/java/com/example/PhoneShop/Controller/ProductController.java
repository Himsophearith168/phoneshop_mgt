package com.example.PhoneShop.Controller;

import com.example.PhoneShop.DTO.PriceDTO;
import com.example.PhoneShop.DTO.ProductImportDTO;
import com.example.PhoneShop.DTO.ProductRequest;
import com.example.PhoneShop.DTO.ProductResponse;
import com.example.PhoneShop.Service.ProductService;
import com.example.PhoneShop.Util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/products", "products"})
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<APIResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        APIResponse<ProductResponse> apiResponse = APIResponse.<ProductResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ProductResponse>>> getProducts() {
        List<ProductResponse> products = productService.getProducts();
        APIResponse<List<ProductResponse>> apiResponse = APIResponse.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .data(products)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> getProductById(@PathVariable("id") Long id) {
        ProductResponse product = productService.getProduct(id);
        APIResponse<ProductResponse> apiResponse = APIResponse.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product retrieved successfully")
                .data(product)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<APIResponse<ProductResponse>> setSalePrice(
            @PathVariable("id") Long id,
            @RequestBody PriceDTO priceDTO) {
        ProductResponse response = productService.setSalePrice(id, priceDTO.getPrice());
        APIResponse<ProductResponse> apiResponse = APIResponse.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product price updated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/import")
    public ResponseEntity<APIResponse<ProductResponse>> importProduct(@Valid @RequestBody ProductImportDTO importDTO) {
        ProductResponse response = productService.importProduct(importDTO);
        APIResponse<ProductResponse> apiResponse = APIResponse.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product imported successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
