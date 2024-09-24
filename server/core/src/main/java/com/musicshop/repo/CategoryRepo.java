package com.musicshop.repo;

import com.musicshop.entity.Category;
import com.musicshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
}
