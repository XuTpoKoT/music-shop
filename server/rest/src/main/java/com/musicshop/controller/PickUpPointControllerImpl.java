package com.musicshop.controller;

import com.musicshop.entity.PickUpPoint;
import com.musicshop.repo.PickUpPointRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PickUpPointControllerImpl implements PickUpPointController {
    private final PickUpPointRepo pickUpPointRepo;

    public List<PickUpPoint> getAllPickUpPoints() {
         return pickUpPointRepo.findAll();
    }
}
