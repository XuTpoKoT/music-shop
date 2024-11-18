package com.musicshop.service;

import com.musicshop.entity.CartItem;
import com.musicshop.repo.CartItemRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemRepo cartItemRepo;

    @Override
    public List<CartItem> findByUserId(Integer userId) {
        return cartItemRepo.findByUserId(userId);
    }

    @Override
    public CartItem saveOnConflictUpdate(CartItem cartItem) {
        return cartItemRepo.saveOnConflictUpdate(cartItem);
    }

    @Override
    public void deleteById(UUID cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }

    @Override
    public CartItem updateCartItem(UUID cartItemId, Integer count) {
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem " + cartItemId + " not found"));
        cartItem.setCount(count);
        return cartItemRepo.save(cartItem);
    }
}
