package com.musicshop.service;


import com.musicshop.dto.MakeOrderDto;
import com.musicshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface OrderService {

    void makeOrder(MakeOrderDto makeOrderDto);
    Page<Order> findByCustomerId(Integer customerId, PageRequest pageable);
    Page<Order> findByEmployeeId(Integer employeeId, PageRequest pageable);

    void updateOrder(UUID id, Order.Status status);
}
