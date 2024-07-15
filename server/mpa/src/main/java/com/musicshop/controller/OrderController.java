package com.musicshop.controller;

import com.musicshop.dto.MakeOrderDto;
import com.musicshop.dto.response.OrderPageResponse;
import com.musicshop.entity.AppUser;
import com.musicshop.entity.Order;
import com.musicshop.entity.OrderItem;
import com.musicshop.error.AccessForbiddenException;
import com.musicshop.mapper.CartItemMapper;
import com.musicshop.mapper.OrderMapper;
import com.musicshop.repo.CartItemRepo;
import com.musicshop.repo.OrderRepo;
import com.musicshop.repo.UserRepo;
import com.musicshop.security.SecurityUser;
import com.musicshop.security.SecurityUtils;
import com.musicshop.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @GetMapping("/{id}")
    public String getOrder(@PathVariable UUID id, Model model) {
        log.info("getOrder called with id " + id);
        Order order = orderRepo.findDetailedById(id).orElseThrow(
                () -> new EntityNotFoundException("Order " + id + " not found")
        );
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        AppUser appUser = securityUser.getAppUser();
        if (appUser.getRole() == AppUser.Role.CUSTOMER && !Objects.equals(order.getCustomer().getId(),
                appUser.getId())) {
            throw new AccessForbiddenException();
        }
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping
    @PreAuthorize("#login == authentication.name")
    public String getOrdersByLogin(@RequestParam String login,
                                   @RequestParam(name = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
                                   @RequestParam(name = "pageSize", defaultValue = "${defaultPageSize}") @Min(1) int pageSize,
                                   Model model) {
        log.info("getOrders called with login " + login);
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        AppUser appUser = securityUser.getAppUser();
        Page<Order> orderPage = new PageImpl<>(new ArrayList<>());
        switch (appUser.getRole()) {
            case CUSTOMER -> {
                orderPage = orderRepo.findByCustomerId(appUser.getId(), PageRequest.of(pageNumber - 1, pageSize));
            } case EMPLOYEE -> {
                orderPage = orderRepo.findByEmployeeId(appUser.getId(), PageRequest.of(pageNumber - 1, pageSize));
            }
        }
        OrderPageResponse orderPageResponse = orderMapper.orderPageToDto(orderPage);
        model.addAttribute("orderPage", orderPageResponse);

        return "orders";
    }

    @PatchMapping("/{id}")
    public String setOrderStatus(@PathVariable UUID id,
                                 @RequestParam String orderStatus) {
        log.info("setOrderStatus called with order id " + id);
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order " + id + " not found")
        );
        order.setStatus(Order.Status.valueOf(orderStatus));
        orderRepo.save(order);
        return "redirect:/v1/orders/" + id;
    }

    @PostMapping
    @PreAuthorize("#login == authentication.name")
    @Transactional
    public String makeOrder(@RequestParam String login,
                            @RequestParam UUID pickUpPointId,
                            @RequestParam(name = "customer", required = false) Integer customerId,
                            @RequestParam(required = false) boolean needSpendBonuses) {
        log.info("makeOrder called with login " + login);
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
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
