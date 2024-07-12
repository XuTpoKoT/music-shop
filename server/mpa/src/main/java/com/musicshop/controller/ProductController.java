package com.musicshop.controller;

import com.musicshop.dto.response.ProductPageResponse;
import com.musicshop.entity.Product;
import com.musicshop.mapper.ProductMapper;
import com.musicshop.repo.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/${api-version}/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    @GetMapping()
    public String getProductsByPageNumber(@RequestParam(name = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
                                          @RequestParam(name = "pageSize", defaultValue = "${defaultPageSize}")
                                          @Min(1) int pageSize, Model model) {
        Page<Product> productPage = productRepo.findAll(PageRequest.of(pageNumber - 1, pageSize));
        ProductPageResponse productDtoPage = productMapper.productPageToDto(productPage);
        model.addAttribute("paginationProducts", productDtoPage);

        return "home";
    }

    @GetMapping("/{id}")
    public String getProductInfo(@PathVariable UUID id, Model model) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product " + id + " not found");
        }
        model.addAttribute("product", productMapper.productToDto(product.get()));

        return "product";
    }
}
