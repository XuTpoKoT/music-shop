package com.musicshop.controller;

import com.musicshop.dto.response.ProductPageResponse;
import com.musicshop.dto.response.ProductResponse;
import com.musicshop.entity.Product;
import com.musicshop.mapper.ProductMapper;
import com.musicshop.service.ProductService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductPageResponse getProducts(
            @RequestParam(name = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "${defaultPageSize}",
                    required = false) @Min(1) int pageSize,
            @RequestParam(name = "categoryId", required = false) UUID categoryId,
            @RequestParam(name = "productPrefix", required = false) String productPrefix) {
        Page<Product> productPage = productService.findAll(PageRequest.of(
                pageNumber - 1, pageSize), categoryId, productPrefix);
        return productMapper.productPageToDto(productPage);
    }

    public ProductResponse getProductInfo(@PathVariable UUID id) {
        log.info("getProductInfo called with " + id);
        Product product = productService.findById(id);
        return productMapper.productToDto(product);
    }
}
