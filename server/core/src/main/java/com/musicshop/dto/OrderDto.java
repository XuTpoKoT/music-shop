package com.musicshop.dto;

import com.musicshop.entity.Order;

import java.time.ZonedDateTime;
import java.util.UUID;

public record OrderDto (
    UUID id,
    String customerUsername,
    ZonedDateTime timestamp,
    Order.Status status,
    String pickUpPointAddress,
    int initialCost,
    int paidByBonuses
) {
}
