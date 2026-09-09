package com.example.PhoneShop.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "colors")
@Data
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long id;

    private String colorName;

}
