package com.musicshop.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddProductToCartRequest(@NotNull(message = "productId не должен быть пустым")
                                      UUID productId) {
}
