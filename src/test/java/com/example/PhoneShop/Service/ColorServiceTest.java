package com.example.PhoneShop.Service;

import com.example.PhoneShop.DTO.ColorRequest;
import com.example.PhoneShop.DTO.ColorResponse;
import com.example.PhoneShop.Entity.ColorEntity;
import com.example.PhoneShop.Exception.APIException;
import com.example.PhoneShop.Mapper.ColorMapper;
import com.example.PhoneShop.Repository.ColorRepository;
import com.example.PhoneShop.Service.Impl.ColorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColorServiceTest {

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private ColorMapper colorMapper;

    private ColorService colorService;

    @BeforeEach
    void setUp() {
        colorService = new ColorServiceImpl(colorRepository, colorMapper);
    }

    @Test
    void testCreateColor_Success() {
        ColorRequest request = new ColorRequest("Black");
        ColorEntity entity = ColorEntity.builder().colorName("Black").build();
        ColorEntity savedEntity = ColorEntity.builder().id(1L).colorName("Black").build();
        ColorResponse response = new ColorResponse(1L, "Black");

        when(colorRepository.existsByColorName("Black")).thenReturn(false);
        when(colorMapper.toEntity(request)).thenReturn(entity);
        when(colorRepository.save(entity)).thenReturn(savedEntity);
        when(colorMapper.toResponse(savedEntity)).thenReturn(response);

        ColorResponse actual = colorService.createColor(request);

        assertThat(actual).isNotNull();
        assertThat(actual.getColorName()).isEqualTo("Black");
        verify(colorRepository).save(entity);
    }

    @Test
    void testCreateColor_Duplicate_ThrowsConflict() {
        ColorRequest request = new ColorRequest("Black");
        when(colorRepository.existsByColorName("Black")).thenReturn(true);

        APIException exception = assertThrows(APIException.class, () -> colorService.createColor(request));
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.CONFLICT);
        verify(colorRepository, never()).save(any());
    }

    @Test
    void testUpdateColor_Duplicate_ThrowsConflict() {
        Long id = 1L;
        ColorRequest request = new ColorRequest("White");
        ColorEntity current = ColorEntity.builder().id(id).colorName("Black").build();
        ColorEntity other = ColorEntity.builder().id(2L).colorName("White").build();

        when(colorRepository.findById(id)).thenReturn(Optional.of(current));
        when(colorRepository.findByColorName("White")).thenReturn(Optional.of(other));

        APIException exception = assertThrows(APIException.class, () -> colorService.updateColor(id, request));
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.CONFLICT);
        verify(colorRepository, never()).save(any());
    }
}