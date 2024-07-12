package com.musicshop.dto.request;

import com.musicshop.entity.Order;

public record SetOrderStatusRequest(Order.Status status) {
}
