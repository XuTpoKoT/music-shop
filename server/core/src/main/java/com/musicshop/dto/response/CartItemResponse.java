package com.musicshop.dto.response;

import java.util.UUID;

public record CartItemResponse(UUID id, String name, int price, String imgRef, Integer count) {
}
