package com.musicshop.mapper;


import com.musicshop.dto.response.ProductPageResponse;
import com.musicshop.dto.response.ProductResponse;
import com.musicshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "manufacturerName", source = "manufacturer.name")
    ProductResponse productToDto(Product product);

    @Mapping(target = "currentPage", expression = "java(productPage.getNumber() + 1)")
    ProductPageResponse productPageToDto(Page<Product> productPage);
}
