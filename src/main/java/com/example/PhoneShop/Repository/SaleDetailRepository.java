package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.SoldDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDetailRepository extends JpaRepository<SoldDetailEntity,Long> {
}
