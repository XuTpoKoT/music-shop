package com.musicshop.service;

import com.musicshop.entity.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartService {
    List<CartItem> findByUserId(Integer userId);
    CartItem saveOnConflictUpdate(CartItem cartItem);
    void deleteById(UUID cartItemId);
    CartItem updateCartItem(UUID cartItemId, Integer count);
}
