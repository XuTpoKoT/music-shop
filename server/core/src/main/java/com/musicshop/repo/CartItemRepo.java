package com.musicshop.repo;

import com.musicshop.entity.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CartItemRepo extends JpaRepository<CartItem, UUID> {
    @EntityGraph(attributePaths = {"product"})
    List<CartItem> findByUserId(Integer id);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cart (user_id, product_id, count) VALUES (:#{#item.userId}," +
            " :#{#item.product.id}, 1) ON CONFLICT (user_id, product_id) DO NOTHING", nativeQuery = true)
    void saveOnConflictIgnore(@Param("item") CartItem cartItem);

    @Modifying
    @Transactional
    void deleteByUserId(Integer userId);
}
