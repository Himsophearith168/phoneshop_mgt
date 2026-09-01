package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.BrandRequest;
import com.example.PhoneShop.DTO.BrandResponse;
import com.example.PhoneShop.Entity.BrandEntity;
import com.example.PhoneShop.Exception.ResourceNotFoundException;
import com.example.PhoneShop.Mapper.BrandMapper;
import com.example.PhoneShop.Repository.BrandRepository;
import com.example.PhoneShop.Service.Impl.BrandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    private BrandService brandService;

    @BeforeEach
    public void setUp() {
        brandService = new BrandServiceImpl(brandRepository, brandMapper);
    }

    // CREATE

    @Test
    public void testCreateBrand() {
        // 1. Given
        BrandRequest brandRequest = BrandRequest.builder()
                .brandName("Apple")
                .description("Apple Inc.")
                .build();
        BrandEntity brandEntity = BrandEntity.builder()
                .name("Apple")
                .description("Apple Inc.")
                .build();
        BrandEntity savedBrand = BrandEntity.builder()
                .id(1L)
                .name("Apple")
                .description("Apple Inc.")
                .build();
        BrandResponse expectedResponse = BrandResponse.builder()
                .id(1L)
                .brandName("Apple")
                .description("Apple Inc.")
                .build();

        when(brandMapper.toEntity(brandRequest)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(savedBrand);
        when(brandMapper.toResponse(savedBrand)).thenReturn(expectedResponse);

        // 2. When
        BrandResponse actualResponse = brandService.createBrand(brandRequest);

        // 3. Then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getId()).isEqualTo(1L);
        assertThat(actualResponse.getBrandName()).isEqualTo("Apple");
        assertThat(actualResponse.getDescription()).isEqualTo("Apple Inc.");

        verify(brandMapper, times(1)).toEntity(brandRequest);
        verify(brandRepository, times(1)).save(brandEntity);
        verify(brandMapper, times(1)).toResponse(savedBrand);
    }

    //GET BY ID

    @Test
    public void testGetBrandById_Success() {
        // 1. Given
        Long brandId = 1L;
        BrandEntity brandEntity = BrandEntity.builder()
                .id(brandId)
                .name("Apple")
                .description("Apple Inc.")
                .build();
        BrandResponse expectedResponse = BrandResponse.builder()
                .id(brandId)
                .brandName("Apple")
                .description("Apple Inc.")
                .build();

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandEntity));
        when(brandMapper.toResponse(brandEntity)).thenReturn(expectedResponse);

        // 2. When
        BrandResponse actualResponse = brandService.getBrand(brandId);

        // 3. Then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getId()).isEqualTo(brandId);
        assertThat(actualResponse.getBrandName()).isEqualTo("Apple");
        assertThat(actualResponse.getDescription()).isEqualTo("Apple Inc.");

        verify(brandRepository, times(1)).findById(brandId);
        verify(brandMapper, times(1)).toResponse(brandEntity);
    }

    @Test
    public void testGetBrandById_NotFound() {
        // 1. Given
        Long brandId = 99L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // 2. When & 3. Then
        assertThrows(ResourceNotFoundException.class, () -> brandService.getBrand(brandId));

        verify(brandRepository, times(1)).findById(brandId);
        verify(brandMapper, never()).toResponse(any());
    }

    //GET ALL

    @Test
    public void testGetAllBrands_Success() {
        // 1. Given
        BrandEntity brand1 = BrandEntity.builder().id(1L).name("Apple").description("Apple Inc.").build();
        BrandEntity brand2 = BrandEntity.builder().id(2L).name("Samsung").description("Samsung Electronics").build();
        BrandResponse response1 = BrandResponse.builder().id(1L).brandName("Apple").description("Apple Inc.").build();
        BrandResponse response2 = BrandResponse.builder().id(2L).brandName("Samsung").description("Samsung Electronics").build();

        when(brandRepository.findAll()).thenReturn(List.of(brand1, brand2));
        when(brandMapper.toResponse(brand1)).thenReturn(response1);
        when(brandMapper.toResponse(brand2)).thenReturn(response2);

        // 2. When
        List<BrandResponse> actualResponses = brandService.getBrands();

        // 3. Then
        assertThat(actualResponses).isNotNull();
        assertThat(actualResponses).hasSize(2);
        assertThat(actualResponses.get(0).getBrandName()).isEqualTo("Apple");
        assertThat(actualResponses.get(1).getBrandName()).isEqualTo("Samsung");

        verify(brandRepository, times(1)).findAll();
        verify(brandMapper, times(1)).toResponse(brand1);
        verify(brandMapper, times(1)).toResponse(brand2);
    }

    //UPDATE

    @Test
    public void testUpdateBrand_Success() {
        // 1. Given
        Long brandId = 1L;
        BrandRequest updateRequest = BrandRequest.builder()
                .brandName("Apple Updated")
                .description("Updated Description")
                .build();
        BrandEntity existingBrand = BrandEntity.builder()
                .id(brandId)
                .name("Apple")
                .description("Apple Inc.")
                .build();
        BrandEntity updatedBrand = BrandEntity.builder()
                .id(brandId)
                .name("Apple Updated")
                .description("Updated Description")
                .build();
        BrandResponse expectedResponse = BrandResponse.builder()
                .id(brandId)
                .brandName("Apple Updated")
                .description("Updated Description")
                .build();

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(existingBrand));
        when(brandRepository.save(existingBrand)).thenReturn(updatedBrand);
        when(brandMapper.toResponse(updatedBrand)).thenReturn(expectedResponse);

        // 2. When
        BrandResponse actualResponse = brandService.updateBrand(brandId, updateRequest);

        // 3. Then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getId()).isEqualTo(brandId);
        assertThat(actualResponse.getBrandName()).isEqualTo("Apple Updated");
        assertThat(actualResponse.getDescription()).isEqualTo("Updated Description");

        verify(brandRepository, times(1)).findById(brandId);
        verify(brandMapper, times(1)).updateEntityFromRequest(updateRequest, existingBrand);
        verify(brandRepository, times(1)).save(existingBrand);
        verify(brandMapper, times(1)).toResponse(updatedBrand);
    }

    @Test
    public void testUpdateBrand_NotFound() {
        // 1. Given
        Long brandId = 99L;
        BrandRequest updateRequest = BrandRequest.builder()
                .brandName("Apple Updated")
                .description("Updated Description")
                .build();

        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // 2. When & 3. Then
        assertThrows(ResourceNotFoundException.class, () -> brandService.updateBrand(brandId, updateRequest));

        verify(brandRepository, times(1)).findById(brandId);
        verify(brandRepository, never()).save(any());
    }

    //DELETE

    @Test
    public void testDeleteBrand_Success() {
        // 1. Given
        Long brandId = 1L;
        BrandEntity existingBrand = BrandEntity.builder()
                .id(brandId)
                .name("Apple")
                .description("Apple Inc.")
                .build();

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(existingBrand));

        // 2. When
        brandService.deleteBrand(brandId);

        // 3. Then
        verify(brandRepository, times(1)).findById(brandId);
        verify(brandRepository, times(1)).delete(existingBrand);
    }

    @Test
    public void testDeleteBrand_NotFound() {
        // 1. Given
        Long brandId = 99L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // 2. When & 3. Then
        assertThrows(ResourceNotFoundException.class, () -> brandService.deleteBrand(brandId));

        verify(brandRepository, times(1)).findById(brandId);
        verify(brandRepository, never()).delete(any(BrandEntity.class));
    }
}
