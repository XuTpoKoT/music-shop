package com.musicshop.controller;

import com.musicshop.dto.response.ProductPageResponse;
import com.musicshop.dto.response.ProductResponse;
import com.musicshop.entity.Product;
import com.musicshop.mapper.ProductMapper;
import com.musicshop.repo.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductControllerImpl implements ProductController {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public ProductPageResponse getProductsByPageNumber(
                                          @RequestParam(name = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
                                          @RequestParam(name = "pageSize", defaultValue = "${defaultPageSize}",
                                                  required = false) @Min(1) int pageSize) {
        Page<Product> productPage = productRepo.findAll(PageRequest.of(pageNumber - 1, pageSize));
        return productMapper.productPageToDto(productPage);
    }

    public ProductResponse getProductInfo(@PathVariable UUID id) {
        log.info("getProductInfo called with " + id);
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product " + id + " not found");
        }
        return productMapper.productToDto(product.get());
    }
}
