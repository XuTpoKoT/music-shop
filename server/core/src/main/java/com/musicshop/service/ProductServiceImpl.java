package com.musicshop.service;

import com.musicshop.entity.Product;
import com.musicshop.repo.ProductRepo;
import com.musicshop.repo.spec.ProductSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    @Override
    public Page<Product> findAll(Pageable pageable, UUID categoryId, String productPrefix) {
        Specification<Product> spec = Specification.where(null);
        if (categoryId != null) {
            spec = spec.and(ProductSpecifications.hasCategory(categoryId));
        }
        if (productPrefix != null) {
            spec = spec.and(ProductSpecifications.hasNamePrefix(productPrefix));
        }
        return productRepo.findAll(spec, pageable);
    }

    @Override
    public Product findById(UUID id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product " + id + " not found");
        };
        return product.get();
    }

}
