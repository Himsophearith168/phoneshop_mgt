package com.example.PhoneShop.Controller;

import com.example.PhoneShop.DTO.ColorRequest;
import com.example.PhoneShop.DTO.ColorResponse;
import com.example.PhoneShop.Service.ColorService;
import com.example.PhoneShop.Util.APIResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/colors", "colors"})
@RequiredArgsConstructor
@Validated
public class ColorController {

    private final ColorService colorService;

    @PostMapping
    public ResponseEntity<APIResponse<ColorResponse>> createColor(@Valid @RequestBody ColorRequest colorRequest) {
        ColorResponse response = colorService.createColor(colorRequest);
        APIResponse<ColorResponse> apiResponse = APIResponse.<ColorResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Color created successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ColorResponse>>> getColors() {
        List<ColorResponse> colors = colorService.getColors();
        APIResponse<List<ColorResponse>> apiResponse = APIResponse.<List<ColorResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Colors retrieved successfully")
                .data(colors)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ColorResponse>> getColorById(@PathVariable("id") @Positive(message = "Color ID must be greater than 0") Long id) {
        ColorResponse colorResponse = colorService.getColor(id);
        APIResponse<ColorResponse> apiResponse = APIResponse.<ColorResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Color retrieved successfully")
                .data(colorResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ColorResponse>> updateColor(
            @PathVariable("id") @Positive(message = "Color ID must be greater than 0") Long id,
            @Valid @RequestBody ColorRequest colorRequest) {
        ColorResponse colorResponse = colorService.updateColor(id, colorRequest);
        APIResponse<ColorResponse> apiResponse = APIResponse.<ColorResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Color updated successfully")
                .data(colorResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteColor(@PathVariable("id") @Positive(message = "Color ID must be greater than 0") Long id) {
        colorService.deleteColor(id);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Color deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}

