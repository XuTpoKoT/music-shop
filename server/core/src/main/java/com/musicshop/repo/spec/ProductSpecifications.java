package com.musicshop.repo.spec;

import com.musicshop.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ProductSpecifications {

    public static Specification<Product> hasNamePrefix(String prefix) {
        return (root, query, cb) -> {
            if (prefix == null || prefix.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), prefix.toLowerCase() + "%");
        };
    }

    public static Specification<Product> hasCategory(UUID categoryId) {
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }
}
