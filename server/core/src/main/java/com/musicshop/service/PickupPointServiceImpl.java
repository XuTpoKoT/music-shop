package com.musicshop.service;

import com.musicshop.entity.PickUpPoint;
import com.musicshop.repo.PickUpPointRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickupPointServiceImpl implements PickupPointService {
    private final PickUpPointRepo pickUpPointRepo;

    public List<PickUpPoint> findAll() {
        return pickUpPointRepo.findAll();
    }
}
