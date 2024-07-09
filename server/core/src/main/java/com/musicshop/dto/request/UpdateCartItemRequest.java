package com.musicshop.dto.request;

import jakarta.validation.constraints.Min;

public record UpdateCartItemRequest(@Min(value = 1, message = "Количество товара должно быть положительным")
                                              Integer count) {
}
