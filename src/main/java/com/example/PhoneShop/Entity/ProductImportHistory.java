package com.example.PhoneShop.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productImportHistories")
@Data
public class ProductImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productImportHistory_id")
    private Long productImportHis_id;

    @Column(name = "date_import")
    private LocalDateTime dateTime;
    @Column(name = "unit_import")
    private Integer importUnit;
    @Column(name = "price_perunit")
    private BigDecimal pricePerUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

}
