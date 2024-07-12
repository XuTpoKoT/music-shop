package com.musicshop.controller;

import com.musicshop.dto.response.UserInfoResponse;
import com.musicshop.entity.AppUser;
import com.musicshop.mapper.UserMapper;
import com.musicshop.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/${api-version}/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @GetMapping("/{login}")
    @PreAuthorize("#login == authentication.name")
    public String getUserInfo(@PathVariable String login, Model model) {
        log.info("getUserInfo for " + login);
        AppUser appUser = userRepo.findByUsername(login).orElseThrow(
                () -> new EntityNotFoundException("User " + login + " not found")
        );
        UserInfoResponse userInfoResponse = userMapper.userToDto(appUser);
        model.addAttribute("user", userInfoResponse);

        return "user";
    }
}
