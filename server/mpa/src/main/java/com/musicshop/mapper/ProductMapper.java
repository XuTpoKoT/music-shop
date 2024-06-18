package com.musicshop.mapper;


import com.musicshop.dto.ProductDto;
import com.musicshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "manufacturerName", source = "manufacturer.name")
    ProductDto toDto(Product product);
}
