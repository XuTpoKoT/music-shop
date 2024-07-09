package com.musicshop.dto.response;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record ProductResponse(UUID id, String name, int price, String description, String color, String manufacturerName,
                              String imgRef, Map<String, String> characteristics) {
}

