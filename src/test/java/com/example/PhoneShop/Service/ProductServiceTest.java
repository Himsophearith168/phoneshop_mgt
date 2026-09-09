package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.ProductImportDTO;
import com.example.PhoneShop.DTO.ProductResponse;
import com.example.PhoneShop.Entity.ProductEntity;
import com.example.PhoneShop.Exception.APIException;
import com.example.PhoneShop.Mapper.ProductMapper;
import com.example.PhoneShop.Repository.ProductImportHistoryRepository;
import com.example.PhoneShop.Repository.ProductRepository;
import com.example.PhoneShop.Service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @Mock private ProductImportHistoryRepository productImportHistoryRepository;
    @Mock private ModelService modelService;
    @Mock private ColorService colorService;
    @Mock private ProductMapper productMapper;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(
                productRepository, productImportHistoryRepository,
                modelService, colorService, productMapper);
    }

    @Test
    void testSetSalePrice_Success() {
        Long productId = 1L;
        BigDecimal price = new BigDecimal("999.99");
        ProductEntity entity = new ProductEntity();
        entity.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(entity));
        when(productRepository.save(any(ProductEntity.class))).thenAnswer(inv -> inv.getArgument(0));
        when(productMapper.toResponse(any(ProductEntity.class))).thenReturn(new ProductResponse());

        ProductResponse response = productService.setSalePrice(productId, price);

        assertThat(response).isNotNull();
        assertThat(entity.getSalePrice()).isEqualTo(price);
        verify(productRepository).save(entity);
    }

    @Test
    void testSetSalePrice_NullPrice_ThrowsBadRequest() {
        APIException ex = assertThrows(APIException.class, () -> productService.setSalePrice(1L, null));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testSetSalePrice_NegativePrice_ThrowsBadRequest() {
        APIException ex = assertThrows(APIException.class, () -> productService.setSalePrice(1L, new BigDecimal("-10.00")));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testImportProduct_NullUnit_ThrowsBadRequest() {
        ProductImportDTO dto = ProductImportDTO.builder()
                .product_id(1L).importUnit(null)
                .importPrice(new BigDecimal("100.00")).importDate(LocalDateTime.now()).build();

        APIException ex = assertThrows(APIException.class, () -> productService.importProduct(dto));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testImportProduct_ZeroUnit_ThrowsBadRequest() {
        ProductImportDTO dto = ProductImportDTO.builder()
                .product_id(1L).importUnit(0)
                .importPrice(new BigDecimal("100.00")).importDate(LocalDateTime.now()).build();

        APIException ex = assertThrows(APIException.class, () -> productService.importProduct(dto));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testImportProduct_NullPrice_ThrowsBadRequest() {
        ProductImportDTO dto = ProductImportDTO.builder()
                .product_id(1L).importUnit(5)
                .importPrice(null).importDate(LocalDateTime.now()).build();

        APIException ex = assertThrows(APIException.class, () -> productService.importProduct(dto));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(productRepository, never()).save(any());
    }
}