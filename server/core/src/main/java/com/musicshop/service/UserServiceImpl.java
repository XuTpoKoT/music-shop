package com.musicshop.service;

import com.musicshop.entity.AppUser;
import com.musicshop.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    @Override
    public AppUser findByUsername(String login) {
        return userRepo.findByUsername(login).orElseThrow(
                () -> new EntityNotFoundException("User " + login + " not found")
        );
    }
}
