package com.musicshop.service;

import com.musicshop.entity.AppUser;

public interface UserService {
    AppUser findByUsername(String login);
}
