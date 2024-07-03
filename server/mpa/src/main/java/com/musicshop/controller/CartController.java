package com.musicshop.controller;

import com.musicshop.entity.AppUser;
import com.musicshop.entity.CartItem;
import com.musicshop.entity.PickUpPoint;
import com.musicshop.entity.Product;
import com.musicshop.mapper.CartItemMapper;
import com.musicshop.repo.CartItemRepo;
import com.musicshop.repo.PickUpPointRepo;
import com.musicshop.repo.ProductRepo;
import com.musicshop.security.SecurityUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/${api-version}/users/{login}/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final PickUpPointRepo pickUpPointRepo;
    private final CartItemMapper cartItemMapper;

    @GetMapping
    @PreAuthorize("#login == authentication.name")
    public String getProductsInCart(@PathVariable String login, Model model) {
        log.info("getProductsInCart called with login " + login);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecurityUser securityUser) {
            AppUser appUser = securityUser.getAppUser();
            List<CartItem> cartItems = cartItemRepo.findByUserId(appUser.getId());
            model.addAttribute("cartItems", cartItemMapper.cartItemsToDto(cartItems));
            List<PickUpPoint> pickUpPoints = pickUpPointRepo.findAll();
            model.addAttribute("pickUpPoints", pickUpPoints);
        }

        return "cart";
    }

    @PostMapping
    @PreAuthorize("#login == authentication.name")
    public String addProductToCart(@PathVariable String login,
                                   @RequestParam UUID productId) {
        log.info("addProductToCart called with login " + login + " and product " + productId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecurityUser securityUser) {
            AppUser appUser = securityUser.getAppUser();
            Product product = productRepo.findById(productId).orElseThrow(() ->
                    new EntityNotFoundException("Product " + productId + " not found"));
            cartItemRepo.saveOnConflictIgnore(new CartItem(appUser.getId(), product, 1));
        }

        return "redirect:/v1/products";
    }

    @DeleteMapping("/{cartItemId}")
    @PreAuthorize("#login == authentication.name")
    public String deleteProductFromCart(@PathVariable String login,
                                        @PathVariable UUID cartItemId) {
        log.info("deleteProductFromCart called with login " + login + " and cartItemId " + cartItemId);
        cartItemRepo.deleteById(cartItemId);

        return "redirect:/v1/users/" + login +"/cart";
    }

    @PatchMapping("/{cartItemId}")
    @PreAuthorize("#login == authentication.name")
    public String updateProductInCart(@PathVariable String login,
                                      @PathVariable UUID cartItemId,
                                      @RequestParam(name = "cnt") @Min(1) Integer count) {
        log.info("updateProductInCart called with login " + login + " and cartItemId " + cartItemId);
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem " + cartItemId + " not found"));

        cartItem.setCount(count);
        cartItemRepo.save(cartItem);

        return "redirect:/v1/users/" + login +"/cart";
    }
}
