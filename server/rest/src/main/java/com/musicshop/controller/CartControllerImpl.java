package com.musicshop.controller;

import com.musicshop.dto.request.AddProductToCartRequest;
import com.musicshop.dto.request.UpdateCartItemRequest;
import com.musicshop.dto.response.CartItemResponse;
import com.musicshop.entity.AppUser;
import com.musicshop.entity.CartItem;
import com.musicshop.entity.Product;
import com.musicshop.mapper.CartItemMapper;
import com.musicshop.repo.CartItemRepo;
import com.musicshop.repo.ProductRepo;
import com.musicshop.security.SecurityUser;
import com.musicshop.security.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CartControllerImpl implements CartController {
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final CartItemMapper cartItemMapper;

    @PreAuthorize("#login == authentication.name")
    public List<CartItemResponse> getProductsInCart(@PathVariable String login) {
        log.info("getProductsInCart called with login " + login);
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        List<CartItem> cartItems = cartItemRepo.findByUserId(securityUser.getAppUser().getId());
        return cartItemMapper.cartItemsToDto(cartItems);
    }

    @PreAuthorize("#login == authentication.name")
    public void addProductToCart(@PathVariable String login, @Valid @RequestBody AddProductToCartRequest request) {
        UUID productId = request.productId();
        log.info("addProductToCart called with login " + login + " and product " + productId);
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        AppUser appUser = securityUser.getAppUser();
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("Product " + productId + " not found"));
        cartItemRepo.saveOnConflictIgnore(new CartItem(appUser.getId(), product, 1));
    }

    @PreAuthorize("#login == authentication.name")
    public void deleteProductFromCart(@PathVariable String login, @PathVariable UUID cartItemId) {
        log.info("deleteProductFromCart called with login " + login + " and cartItemId " + cartItemId);
        cartItemRepo.deleteById(cartItemId);
    }

    @PreAuthorize("#login == authentication.name")
    public void updateProductInCart(@PathVariable String login,
                                    @PathVariable UUID cartItemId,
                                    @Valid @RequestBody UpdateCartItemRequest request) {
        log.info("updateProductInCart called with login " + login + " and cartItemId " + cartItemId);
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem " + cartItemId + " not found"));
        cartItem.setCount(request.count());
        cartItemRepo.save(cartItem);
    }
}
