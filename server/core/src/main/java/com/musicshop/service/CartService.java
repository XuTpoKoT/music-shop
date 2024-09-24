package com.musicshop.service;

import com.musicshop.entity.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartService {
    List<CartItem> findByUserId(Integer userId);
    void saveOnConflictIgnore(CartItem cartItem);
    void deleteById(UUID cartItemId);
    void updateCartItem(UUID cartItemId, Integer count);
}
