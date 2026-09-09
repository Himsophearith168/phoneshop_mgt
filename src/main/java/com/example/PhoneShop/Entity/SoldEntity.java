package com.example.PhoneShop.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "solds")
@Data
public class SoldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sold_id")
    private Long id;

    @Column(name = "sold_date")
    private LocalDateTime soldDate;
}
