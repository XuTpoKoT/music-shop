package com.musicshop.controller;

import com.musicshop.entity.Product;
import com.musicshop.repo.ProductRepo;
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

    @GetMapping()
    public String getProductsByPageNumber(@RequestParam(name = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
                                          @RequestParam(name = "pageSize", defaultValue = "3")  // TODO
                                          @Min(1) int pageSize, Model model) {
        Page<Product> pageOfProducts = productRepo.findAll(PageRequest.of(pageNumber, pageSize));
        model.addAttribute("paginationProducts", pageOfProducts);

        return "home";
    }

    @GetMapping("/{id}")
    public String getProductInfo(@PathVariable String id, Model model) {
        Optional<Product> product = productRepo.findById(UUID.fromString(id));
        model.addAttribute("product", product);

        return "product";
    }
}
