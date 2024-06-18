package com.musicshop.mapper;


import com.musicshop.dto.ProductDto;
import com.musicshop.dto.ProductPageDto;
import com.musicshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "manufacturerName", source = "manufacturer.name")
    ProductDto productToDto(Product product);

    @Mapping(target = "currentPage", expression = "java(productPage.getNumber() + 1)")
    ProductPageDto productPageToDto(Page<Product> productPage);
}
