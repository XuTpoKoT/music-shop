package com.musicshop.dto.response;

import com.musicshop.entity.Order;

import java.time.ZonedDateTime;
import java.util.UUID;

public record OrderResponse(
    UUID id,
    String customerUsername,
    ZonedDateTime timestamp,
    Order.Status status,
    String pickUpPointAddress,
    int initialCost,
    int paidByBonuses
) {
}
