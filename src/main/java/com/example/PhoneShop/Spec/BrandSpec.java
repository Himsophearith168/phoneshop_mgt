package com.example.PhoneShop.Spec;

import com.example.PhoneShop.Entity.BrandEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BrandSpec implements Specification<BrandEntity> {
    private final BrandFilter brandFilter;

    @Override
    public Predicate toPredicate(Root<BrandEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (brandFilter != null) {
            Long id = brandFilter.getIdValue();
            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            String name = brandFilter.getNameValue();
            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
            }
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
