package com.example.PhoneShop.Service.Impl;

import com.example.PhoneShop.DTO.ProductImportDTO;
import com.example.PhoneShop.DTO.ProductRequest;
import com.example.PhoneShop.DTO.ProductResponse;
import com.example.PhoneShop.Entity.ColorEntity;
import com.example.PhoneShop.Entity.ModelEntity;
import com.example.PhoneShop.Entity.ProductEntity;
import com.example.PhoneShop.Entity.ProductImportHistory;
import com.example.PhoneShop.Exception.APIException;
import com.example.PhoneShop.Exception.ResourceNotFoundException;
import com.example.PhoneShop.Mapper.ProductMapper;
import com.example.PhoneShop.Repository.ProductImportHistoryRepository;
import com.example.PhoneShop.Repository.ProductRepository;
import com.example.PhoneShop.Service.ColorService;
import com.example.PhoneShop.Service.ModelService;
import com.example.PhoneShop.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImportHistoryRepository productImportHistoryRepository;
    private final ModelService modelService;
    private final ColorService colorService;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        ModelEntity model = modelService.getById(request.getModels_id());
        ColorEntity color = colorService.getColorById(request.getColor_id());

        if (productRepository.existsByModelIdAndColorId(model.getId(), color.getId())) {
            throw new APIException(HttpStatus.CONFLICT, "Product with model and color already exists");
        }

        ProductEntity product = productMapper.toEntity(model, color);
        ProductEntity savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public ProductResponse getProduct(Long id) {
        ProductEntity product = getProductById(id);
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse setSalePrice(Long id, BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Price must be greater than 0");
        }
        ProductEntity product = getProductById(id);
        product.setSalePrice(price);
        ProductEntity savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse importProduct(ProductImportDTO importDTO) {
        if (importDTO.getImportUnit() == null || importDTO.getImportUnit() <= 0) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Import unit must be greater than 0");
        }
        if (importDTO.getImportPrice() == null || importDTO.getImportPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Price must be greater than 0");
        }
        ProductEntity product = getProductById(importDTO.getProduct_id());
        int currentUnit = product.getAvailableUnit() == null ? 0 : product.getAvailableUnit();
        product.setAvailableUnit(currentUnit + importDTO.getImportUnit());
        ProductEntity updatedProduct = productRepository.save(product);

        ProductImportHistory history = new ProductImportHistory();
        history.setProduct(product);
        history.setImportUnit(importDTO.getImportUnit());
        history.setPricePerUnit(importDTO.getImportPrice());
        history.setDateTime(importDTO.getImportDate());
        productImportHistoryRepository.save(history);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public Void validateStock(Long productId, Integer numberOfUnit) {
        return null;
    }

    @Override
    @Transactional
    public Map<Integer, String> uploadProduct(MultipartFile file) {
        Map<Integer, String> map = new HashMap<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new APIException(HttpStatus.BAD_REQUEST, "Excel file has no sheets");
            }
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            int rowIdx = 1;
            while (rowIterator.hasNext()) {
                rowIdx++;
                Row row = rowIterator.next();
                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                Integer rowNumber = rowIdx;
                try {
                    int cellIndex = 0;

                    // 1. Row No
                    Cell cellNo = row.getCell(cellIndex++);
                    if (cellNo != null) {
                        rowNumber = getNumericValueAsInt(cellNo);
                    }

                    // 2. Model ID
                    Cell cellModelId = row.getCell(cellIndex++);
                    if (cellModelId == null) {
                        throw new APIException(HttpStatus.BAD_REQUEST, "Model ID is required");
                    }
                    Long modelId = getNumericValueAsLong(cellModelId);

                    // 3. Color ID
                    Cell cellColorId = row.getCell(cellIndex++);
                    if (cellColorId == null) {
                        throw new APIException(HttpStatus.BAD_REQUEST, "Color ID is required");
                    }
                    Long colorId = getNumericValueAsLong(cellColorId);

                    // 4. Import Price
                    Cell cellImportPrice = row.getCell(cellIndex++);
                    if (cellImportPrice == null) {
                        throw new APIException(HttpStatus.BAD_REQUEST, "Import price is required");
                    }
                    Double importPrice = getNumericValueAsDouble(cellImportPrice);
                    if (importPrice <= 0) {
                        throw new APIException(HttpStatus.BAD_REQUEST, "Price must be greater than 0");
                    }

                    // 5. Import Unit
                    Cell cellImportUnit = row.getCell(cellIndex++);
                    if (cellImportUnit == null) {
                        throw new APIException(HttpStatus.BAD_REQUEST, "Import unit is required");
                    }
                    Integer importUnit = getNumericValueAsInt(cellImportUnit);
                    if (importUnit < 1) {
                        throw new APIException(HttpStatus.BAD_REQUEST, "Unit must be greater than 0");
                    }

                    // 6. Import Date
                    Cell cellImportDate = row.getCell(cellIndex++);
                    LocalDateTime importDate = parseCellDateTime(cellImportDate);

                    // Find or create product
                    ProductEntity product = productRepository.findByModelIdAndColorId(modelId, colorId)
                            .orElseGet(() -> {
                                ModelEntity model = modelService.getById(modelId);
                                ColorEntity color = colorService.getColorById(colorId);
                                ProductEntity newProduct = productMapper.toEntity(model, color);
                                return productRepository.save(newProduct);
                            });

                    int currentAvailable = product.getAvailableUnit() != null ? product.getAvailableUnit() : 0;
                    product.setAvailableUnit(currentAvailable + importUnit);
                    productRepository.save(product);

                    // Save product import history
                    ProductImportHistory importHistory = new ProductImportHistory();
                    importHistory.setDateTime(importDate);
                    importHistory.setImportUnit(importUnit);
                    importHistory.setPricePerUnit(BigDecimal.valueOf(importPrice));
                    importHistory.setProduct(product);
                    productImportHistoryRepository.save(importHistory);

                } catch (Exception e) {
                    log.error("Error processing row {}: {}", rowNumber, e.getMessage(), e);
                    map.put(rowNumber, e.getMessage());
                }
            }

        } catch (IOException e) {
            log.error("Failed to read excel file: {}", e.getMessage(), e);
            throw new APIException(HttpStatus.BAD_REQUEST, "Failed to read excel file: " + e.getMessage());
        }
        return map;
    }

    private boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private Integer getNumericValueAsInt(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Integer.parseInt(cell.getStringCellValue().trim());
        }
        throw new IllegalArgumentException("Invalid cell value for integer: " + cell);
    }

    private Long getNumericValueAsLong(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Long.parseLong(cell.getStringCellValue().trim());
        }
        throw new IllegalArgumentException("Invalid cell value for long: " + cell);
    }

    private Double getNumericValueAsDouble(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Double.parseDouble(cell.getStringCellValue().trim());
        }
        throw new IllegalArgumentException("Invalid cell value for numeric: " + cell);
    }

    private LocalDateTime parseCellDateTime(Cell cell) {
        if (cell == null) {
            return LocalDateTime.now();
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue();
            }
            return cell.getDateCellValue().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        } else if (cell.getCellType() == CellType.STRING) {
            String val = cell.getStringCellValue().trim();
            if (val.isEmpty()) {
                return LocalDateTime.now();
            }
            try {
                return LocalDateTime.parse(val);
            } catch (Exception ignored) {
                try {
                    return LocalDateTime.parse(val, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                } catch (Exception ignored2) {
                    return LocalDate.parse(val, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
                }
            }
        }
        return LocalDateTime.now();
    }
}
