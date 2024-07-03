package com.musicshop.dto;

import java.util.List;

public record ProductPageDto(int totalPages, int currentPage, List<ProductDto> content) {
}

