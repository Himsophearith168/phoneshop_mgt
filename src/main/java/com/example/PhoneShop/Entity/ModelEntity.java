package com.example.PhoneShop.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "models")

public class ModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modelID")
    private Long id;
    private String modelName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;
}
