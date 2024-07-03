package com.musicshop.mapper;


import com.musicshop.dto.OrderDto;
import com.musicshop.dto.OrderPageDto;
import com.musicshop.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customerUsername", source = "customer.username")
    @Mapping(target = "pickUpPointAddress", source = "pickUpPoint.address")
    OrderDto orderToDto(Order entity);

    @Mapping(target = "currentPage", expression = "java(entity.getNumber() + 1)")
    OrderPageDto orderPageToDto(Page<Order> entity);
}
