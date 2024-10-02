package com.musicshop.controller;

import com.musicshop.dto.MakeOrderDto;
import com.musicshop.dto.request.MakeOrderRequest;
import com.musicshop.dto.request.SetOrderStatusRequest;
import com.musicshop.dto.response.OrderPageResponse;
import com.musicshop.dto.response.OrderResponse;
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
import com.musicshop.service.CartService;
import com.musicshop.service.OrderService;
import com.musicshop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;
    private final CartItemMapper cartItemMapper;
    private final OrderMapper orderMapper;

    @PreAuthorize("#login == authentication.name")
    public OrderPageResponse getOrdersByLogin(@RequestParam String login,
                                              @RequestParam(defaultValue = "1") @Min(1) int pageNumber,
                                              @RequestParam(defaultValue = "${defaultPageSize}") @Min(1) int pageSize) {
        log.info("getOrders called with login " + login);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = securityUser.getAppUser();
        Page<Order> orderPage = new PageImpl<>(new ArrayList<>());
        switch (appUser.getRole()) {
            case CUSTOMER -> orderPage = orderService.findByCustomerId(appUser.getId(),
                    PageRequest.of(pageNumber - 1, pageSize));
            case EMPLOYEE -> orderPage = orderService.findByEmployeeId(appUser.getId(),
                    PageRequest.of(pageNumber - 1, pageSize));
        }
        return orderMapper.orderPageToDto(orderPage);
    }

    @PreAuthorize("#login == authentication.name")
    @Transactional
    public void makeOrder(@RequestParam String login,
                          @RequestBody MakeOrderRequest makeOrderRequest) {
        log.info("makeOrder called with login " + login);
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        AppUser appUser = userService.findById(securityUser.getAppUser().getId());
        AppUser customer = appUser;
        Integer employeeId = null;
        if (appUser.getRole() == AppUser.Role.EMPLOYEE) {
            log.info("customer id " + makeOrderRequest.customerId());
            if (makeOrderRequest.customerId() == null) {
                customer = null;
            } else try {
                log.info("find customer with id " + makeOrderRequest.customerId());
                customer = userService.findById(makeOrderRequest.customerId());
            } catch (EntityNotFoundException e) {
                customer = null;
            }
            employeeId = appUser.getId();
        }
        List<OrderItem> orderItems = cartItemMapper.cartItemsToOrderItems(
                cartService.findByUserId(appUser.getId()));

        MakeOrderDto makeOrderDto = MakeOrderDto.builder()
                .employeeId(employeeId)
                .customer(customer)
                .timestamp(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("UTC")))
                .pickUpPointId(makeOrderRequest.pickUpPointId())
                .needSpendBonuses(makeOrderRequest.needSpendBonuses())
                .orderItems(orderItems)
                .status(Order.Status.formed)
                .build();
        orderService.makeOrder(makeOrderDto);
    }

    public void setOrderStatus(@PathVariable UUID id,
                               @RequestBody SetOrderStatusRequest request) {
        log.info("setOrderStatus called with order id " + id);
        orderService.updateOrder(id, request.status());
    }
}
