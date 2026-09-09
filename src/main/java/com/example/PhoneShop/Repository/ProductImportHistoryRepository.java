package com.example.PhoneShop.Repository;

import com.example.PhoneShop.Entity.ProductImportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImportHistoryRepository extends JpaRepository<ProductImportHistory, Long> {
}
