package com.musicshop.dto.response;

import java.util.List;

public record ProductPageResponse(int totalPages, int currentPage, List<ProductResponse> content) {
}

