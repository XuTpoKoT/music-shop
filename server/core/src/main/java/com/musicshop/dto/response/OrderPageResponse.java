package com.musicshop.dto.response;

import java.util.List;

public record OrderPageResponse(int totalPages, int currentPage, List<OrderResponse> content) {
}

