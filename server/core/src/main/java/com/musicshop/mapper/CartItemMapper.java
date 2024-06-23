package com.musicshop.mapper;


import com.musicshop.dto.CartItemResponse;
import com.musicshop.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = ".", source = "product")
    CartItemResponse cartItemToDto(CartItem entity);
    List<CartItemResponse> cartItemsToDto(List<CartItem> entity);
}
