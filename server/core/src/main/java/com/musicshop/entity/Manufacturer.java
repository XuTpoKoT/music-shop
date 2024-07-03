package com.musicshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "manufacturers")
@NoArgsConstructor
@Getter
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;

    public Manufacturer(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}

