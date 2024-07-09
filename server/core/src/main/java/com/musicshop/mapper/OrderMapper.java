package com.musicshop.mapper;


import com.musicshop.dto.response.OrderPageResponse;
import com.musicshop.dto.response.OrderResponse;
import com.musicshop.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customerUsername", source = "customer.username")
    @Mapping(target = "pickUpPointAddress", source = "pickUpPoint.address")
    OrderResponse orderToDto(Order entity);

    @Mapping(target = "currentPage", expression = "java(entity.getNumber() + 1)")
    OrderPageResponse orderPageToDto(Page<Order> entity);
}
