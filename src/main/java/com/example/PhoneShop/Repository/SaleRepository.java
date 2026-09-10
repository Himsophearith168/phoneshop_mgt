package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.SoldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<SoldEntity,Long> {
}
