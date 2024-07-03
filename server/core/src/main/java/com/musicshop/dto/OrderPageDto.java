package com.musicshop.dto;

import java.util.List;

public record OrderPageDto(int totalPages, int currentPage, List<OrderDto> content) {
}

