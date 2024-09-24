package com.musicshop.controller;

import com.musicshop.dto.response.UserInfoResponse;
import com.musicshop.entity.AppUser;
import com.musicshop.mapper.UserMapper;
import com.musicshop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("#login == authentication.name")
    public UserInfoResponse getUserInfo(@PathVariable String login) {
        log.info("getUserInfo for " + login);
        AppUser appUser = userService.findByUsername(login);
        return userMapper.userToDto(appUser);
    }
}
