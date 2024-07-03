package com.musicshop.controller;

import com.musicshop.dto.UserInfoResponse;
import com.musicshop.entity.AppUser;
import com.musicshop.mapper.UserMapper;
import com.musicshop.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserMapper userMapper;

    @GetMapping("/{login}")
    @PreAuthorize("#login == authentication.name")
    public String getUserInfo(@PathVariable String login, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecurityUser securityUser) {
            AppUser appUser = securityUser.getAppUser();
            UserInfoResponse userInfoResponse = userMapper.userToDto(appUser);
            model.addAttribute("user", userInfoResponse);
        }

        return "user";
    }
}
