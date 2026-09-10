package com.example.PhoneShop.Controller;

import com.example.PhoneShop.DTO.SaleDTO;
import com.example.PhoneShop.Service.SaleService;
import com.example.PhoneShop.Util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<APIResponse<Void>> sell(@Valid @RequestBody SaleDTO saleDTO) {
        saleService.sell(saleDTO);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Sale recorded successfully")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
