package com.musicshop.dto;

import java.util.UUID;

public record CartItemResponse(UUID id, String name, int price, String imgRef, Integer count) {
}
