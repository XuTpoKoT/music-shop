package com.musicshop.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MakeOrderRequest(Integer customerId,
                               @NotNull(message = "pickUpPointId не должен быть null")
                               UUID pickUpPointId,
                               @NotNull(message = "needSpendBonuses не должен быть null")
                               boolean needSpendBonuses) {
}
