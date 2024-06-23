package com.musicshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "cart"
        , uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "product_id"}))
@Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id")
    private Integer userId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @Setter
    private Integer count;

    public CartItem(Integer userId, Product product, Integer count) {
        this.userId = userId;
        this.product = product;
        this.count = count;
    }
}
