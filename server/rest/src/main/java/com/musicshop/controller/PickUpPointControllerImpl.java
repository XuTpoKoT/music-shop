package com.musicshop.controller;

import com.musicshop.entity.PickUpPoint;
import com.musicshop.repo.PickUpPointRepo;
import com.musicshop.service.PickupPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PickUpPointControllerImpl implements PickUpPointController {
    private final PickupPointService pickupPointService;

    public List<PickUpPoint> getAllPickUpPoints() {
         return pickupPointService.findAll();
    }
}
