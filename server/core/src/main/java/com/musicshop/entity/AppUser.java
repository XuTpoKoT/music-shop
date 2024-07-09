package com.musicshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class AppUser {
    public enum Role {
        CUSTOMER,
        EMPLOYEE,
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private Integer bonuses;

    public AppUser(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        if (role == Role.CUSTOMER) {
            this.bonuses = 0;
        }
    }

    public AppUser(Integer id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        if (role == Role.CUSTOMER) {
            this.bonuses = 0;
        }
    }

    public void incBonuses(int accruedBonuses) {
        this.bonuses += accruedBonuses;
    }
}
