package com.musicshop.controller;

import com.musicshop.dto.response.UserInfoResponse;
import com.musicshop.entity.AppUser;
import com.musicshop.mapper.UserMapper;
import com.musicshop.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @PreAuthorize("#login == authentication.name")
    public UserInfoResponse getUserInfo(@PathVariable String login) {
        log.info("getUserInfo for " + login);
        AppUser appUser = userRepo.findByUsername(login).orElseThrow(
                () -> new EntityNotFoundException("User " + login + " not found")
        );
        return userMapper.userToDto(appUser);
    }
}
