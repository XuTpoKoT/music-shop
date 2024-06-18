package com.musicshop.dto;

import java.util.Map;
import java.util.UUID;

public record ProductDto (UUID id, String name, int price, String description, String color, String manufacturerName,
                          String imgRef, Map<String, String> characteristics) {
}

