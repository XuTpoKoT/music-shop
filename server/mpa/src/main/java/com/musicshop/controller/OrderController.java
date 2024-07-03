package com.musicshop.controller;

import com.musicshop.dto.MakeOrderDto;
import com.musicshop.dto.OrderPageDto;
import com.musicshop.entity.AppUser;
import com.musicshop.entity.Order;
import com.musicshop.entity.OrderItem;
import com.musicshop.mapper.CartItemMapper;
import com.musicshop.mapper.OrderMapper;
import com.musicshop.repo.CartItemRepo;
import com.musicshop.repo.OrderRepo;
import com.musicshop.repo.UserRepo;
import com.musicshop.security.SecurityUser;
import com.musicshop.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/${api-version}/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final CartItemRepo cartItemRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final CartItemMapper cartItemMapper;
    private final OrderMapper orderMapper;

    @GetMapping
    @PreAuthorize("#login == authentication.name")
    public String getOrdersByLogin(@RequestParam String login,
                                   @RequestParam(name = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
                                   @RequestParam(name = "pageSize", defaultValue = "6") @Min(1) int pageSize, // TODO
                                   Model model) {
        log.info("getOrders called with login " + login);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = securityUser.getAppUser();
        Page<Order> orderPage = new PageImpl<>(new ArrayList<>());
        switch (appUser.getRole()) {
            case CUSTOMER -> {
                orderPage = orderRepo.findByCustomerId(appUser.getId(), PageRequest.of(pageNumber - 1, pageSize));
            } case EMPLOYEE -> {
                orderPage = orderRepo.findByEmployeeId(appUser.getId(), PageRequest.of(pageNumber - 1, pageSize));
            }
        }
        OrderPageDto orderPageDto = orderMapper.orderPageToDto(orderPage);
        model.addAttribute("orderPage", orderPageDto);

        return "orders";
    }

    @PostMapping
    @PreAuthorize("#login == authentication.name")
    public String makeOrder(@RequestParam String login,
                            @RequestParam UUID pickUpPointId,
                            @RequestParam(name = "customer", required = false) Integer customerId,
                            @RequestParam(required = false) boolean needSpendBonuses) {
        log.info("makeOrder called with login " + login);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = userRepo.findById(securityUser.getAppUser().getId()).orElseThrow(
                () -> new EntityNotFoundException("User " + securityUser.getAppUser().getId() + " not found")
        );
        AppUser customer = appUser;
        Integer employeeId = null;
        if (appUser.getRole() == AppUser.Role.EMPLOYEE) {
            customer = userRepo.findById(customerId).orElse(null);
            employeeId = appUser.getId();
        }
        List<OrderItem> orderItems = cartItemMapper.cartItemsToOrderItems(
                cartItemRepo.findByUserId(appUser.getId()));

        MakeOrderDto makeOrderDto = MakeOrderDto.builder()
                .employeeId(employeeId)
                .customer(customer)
                .timestamp(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UTC")))
                .pickUpPointId(pickUpPointId)
                .needSpendBonuses(needSpendBonuses)
                .orderItems(orderItems)
                .status(Order.Status.formed)
                .build();
        orderService.makeOrder(makeOrderDto);

        return "redirect:/v1/products";
    }
}
