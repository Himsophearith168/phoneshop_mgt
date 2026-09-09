package com.example.PhoneShop.Service.Impl;

import com.example.PhoneShop.DTO.ColorRequest;
import com.example.PhoneShop.DTO.ColorResponse;
import com.example.PhoneShop.Entity.ColorEntity;
import com.example.PhoneShop.Exception.APIException;
import com.example.PhoneShop.Exception.ResourceNotFoundException;
import com.example.PhoneShop.Mapper.ColorMapper;
import com.example.PhoneShop.Repository.ColorRepository;
import com.example.PhoneShop.Service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public ColorResponse createColor(ColorRequest colorRequest) {
        if (colorRepository.existsByColorName(colorRequest.getColorName())) {
            throw new APIException(HttpStatus.CONFLICT, "Color with name " + colorRequest.getColorName() + " already exists");
        }
        ColorEntity colorEntity = colorMapper.toEntity(colorRequest);
        ColorEntity savedColor = colorRepository.save(colorEntity);
        return colorMapper.toResponse(savedColor);
    }

    @Override
    public ColorEntity createColor(ColorEntity color) {
        if (colorRepository.existsByColorName(color.getColorName())) {
            throw new APIException(HttpStatus.CONFLICT, "Color with name " + color.getColorName() + " already exists");
        }
        return colorRepository.save(color);
    }

    @Override
    public ColorResponse updateColor(Long id, ColorRequest colorRequest) {
        ColorEntity colorEntity = getColorById(id);
        colorRepository.findByColorName(colorRequest.getColorName()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new APIException(HttpStatus.CONFLICT, "Color with name " + colorRequest.getColorName() + " already exists");
            }
        });
        colorMapper.updateEntityFromRequest(colorRequest, colorEntity);
        ColorEntity updatedColor = colorRepository.save(colorEntity);
        return colorMapper.toResponse(updatedColor);
    }

    @Override
    public void deleteColor(Long id) {
        ColorEntity colorEntity = getColorById(id);
        colorRepository.delete(colorEntity);
    }

    @Override
    public ColorResponse getColor(Long id) {
        ColorEntity colorEntity = getColorById(id);
        return colorMapper.toResponse(colorEntity);
    }

    @Override
    public ColorEntity getColorById(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color", "id", id));
    }

    @Override
    public List<ColorResponse> getColors() {
        return colorRepository.findAll()
                .stream()
                .map(colorMapper::toResponse)
                .collect(Collectors.toList());
    }
}
