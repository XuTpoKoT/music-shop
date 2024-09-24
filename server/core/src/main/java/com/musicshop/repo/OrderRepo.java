package com.musicshop.repo;

import com.musicshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {
    @EntityGraph(attributePaths = {"customer", "pickUpPoint"})
    Optional<Order> findDetailedById(UUID id);
    @EntityGraph(attributePaths = {"customer", "pickUpPoint"})
    Page<Order> findByCustomerId(Integer id, PageRequest of);
    @EntityGraph(attributePaths = {"customer", "pickUpPoint"})
    Page<Order> findByEmployeeId(Integer id, PageRequest of);
}
