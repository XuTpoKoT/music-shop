package com.musicshop.service;

import com.musicshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Product findById(UUID id);
}
