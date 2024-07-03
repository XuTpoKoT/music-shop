package com.musicshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class Order {
    public enum Status {
        formed("оформлен"),
        built("собран"),
        delivered("доставлен"),
        received("получен");
        private String name;
        public String getName() {
            return name;
        }

        Status(String str) {
            name = str;
        }
    }
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private AppUser customer;
    private Integer employeeId;
    @Column(nullable = false)
    private ZonedDateTime timestamp;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_point_id", nullable = false)
    private PickUpPoint pickUpPoint;
    private int initialCost;
    private int paidByBonuses;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public void setOrderItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(this);
        }
        this.orderItems = orderItems;
    }

    @Builder
    public Order(AppUser customer, Integer employeeId, ZonedDateTime timestamp, Status status, PickUpPoint pickUpPoint,
                 int initialCost, int paidByBonuses) {
        this.customer = customer;
        this.employeeId = employeeId;
        this.timestamp = timestamp;
        this.status = status;
        this.pickUpPoint = pickUpPoint;
        this.initialCost = initialCost;
        this.paidByBonuses = paidByBonuses;
    }
}
