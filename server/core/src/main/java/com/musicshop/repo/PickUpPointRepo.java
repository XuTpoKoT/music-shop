package com.musicshop.repo;

import com.musicshop.entity.PickUpPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PickUpPointRepo extends JpaRepository<PickUpPoint, UUID> {
}
