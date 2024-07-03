package com.musicshop.repo;

import com.musicshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {
    Page<Order> findByCustomerId(Integer id, PageRequest of);
    Page<Order> findByEmployeeId(Integer id, PageRequest of);
}
