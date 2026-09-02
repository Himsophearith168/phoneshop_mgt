package com.example.PhoneShop.Controller;

import com.example.PhoneShop.DTO.ModelRequest;
import com.example.PhoneShop.DTO.ModelResponse;
import com.example.PhoneShop.Entity.ModelEntity;
import com.example.PhoneShop.Service.ModelService;
import com.example.PhoneShop.Util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/models")
@RequiredArgsConstructor
public class ModelController {
    private final ModelService modelService;

    @PostMapping
    public ResponseEntity<APIResponse<ModelResponse>> createModel(@Valid @RequestBody ModelRequest modelRequest) {
        ModelResponse response = modelService.createModel(modelRequest);
        APIResponse<ModelResponse> apiResponse = APIResponse.<ModelResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Model created successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<APIResponse<List<ModelEntity>>> getByBrand(@PathVariable("brandId") Long brandId) {
        List<ModelEntity> models = modelService.getByBrand(brandId);
        APIResponse<List<ModelEntity>> apiResponse = APIResponse.<List<ModelEntity>>builder()
                .status(HttpStatus.OK.value())
                .message("Models retrieved successfully")
                .data(models)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
