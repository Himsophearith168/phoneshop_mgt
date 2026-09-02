package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.ModelRequest;
import com.example.PhoneShop.DTO.ModelResponse;
import com.example.PhoneShop.Entity.BrandEntity;
import com.example.PhoneShop.Entity.ModelEntity;
import com.example.PhoneShop.Mapper.ModelMapper;
import com.example.PhoneShop.Repository.ModelRepository;
import com.example.PhoneShop.Service.Impl.ModelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BrandService brandService;

    private ModelService modelService;

    @BeforeEach
    void setUp() {
        modelService = new ModelServiceImpl(modelRepository, modelMapper, brandService);
    }

    @Test
    void testCreateModel_Success() {
        ModelRequest request = ModelRequest.builder()
                .modelName("iPhone 15 Pro")
                .description("Titanium")
                .brandId(1L)
                .build();

        BrandEntity brandEntity = BrandEntity.builder()
                .id(1L)
                .name("Apple")
                .build();

        ModelEntity entityToSave = new ModelEntity();
        entityToSave.setModelName("iPhone 15 Pro");
        entityToSave.setDescription("Titanium");

        ModelEntity savedEntity = new ModelEntity();
        savedEntity.setId(10L);
        savedEntity.setModelName("iPhone 15 Pro");
        savedEntity.setDescription("Titanium");
        savedEntity.setBrand(brandEntity);

        ModelResponse expectedResponse = ModelResponse.builder()
                .modelId(10L)
                .modelName("iPhone 15 Pro")
                .modelDescription("Titanium")
                .brandId(1L)
                .build();

        when(brandService.getById(1L)).thenReturn(brandEntity);
        when(modelMapper.toEntity(request)).thenReturn(entityToSave);
        when(modelRepository.save(entityToSave)).thenReturn(savedEntity);
        when(modelMapper.toResponse(savedEntity)).thenReturn(expectedResponse);

        ModelResponse actualResponse = modelService.createModel(request);

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getModelId()).isEqualTo(10L);
        assertThat(actualResponse.getModelName()).isEqualTo("iPhone 15 Pro");
        assertThat(actualResponse.getBrandId()).isEqualTo(1L);

        verify(brandService, times(1)).getById(1L);
        verify(modelMapper, times(1)).toEntity(request);
        verify(modelRepository, times(1)).save(entityToSave);
        verify(modelMapper, times(1)).toResponse(savedEntity);
    }

    @Test
    void testGetByBrand() {
        Long brandId = 1L;
        ModelEntity model1 = new ModelEntity();
        model1.setId(10L);
        model1.setModelName("iPhone 15");

        when(modelRepository.findByBrandId(brandId)).thenReturn(List.of(model1));

        List<ModelEntity> result = modelService.getByBrand(brandId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getModelName()).isEqualTo("iPhone 15");
        verify(modelRepository, times(1)).findByBrandId(brandId);
    }
}
