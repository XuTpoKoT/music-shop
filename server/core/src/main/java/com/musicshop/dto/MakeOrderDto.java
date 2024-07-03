package com.musicshop.dto;

import com.musicshop.entity.AppUser;
import com.musicshop.entity.Order;
import com.musicshop.entity.OrderItem;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record MakeOrderDto(UUID id, AppUser customer, Integer employeeId, ZonedDateTime timestamp,
                           Order.Status status, UUID pickUpPointId, List<OrderItem> orderItems,
                           boolean needSpendBonuses) {
    @Builder
    public MakeOrderDto {
    }
}
