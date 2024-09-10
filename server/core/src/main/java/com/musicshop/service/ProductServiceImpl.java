package com.musicshop.service;

import com.musicshop.entity.Product;
import com.musicshop.repo.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAll(pageable);
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
