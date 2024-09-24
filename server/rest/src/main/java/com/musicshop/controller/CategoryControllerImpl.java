package com.musicshop.controller;

import com.musicshop.entity.Category;
import com.musicshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {
    private final CategoryService categoryService;
    @Override
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }
}
