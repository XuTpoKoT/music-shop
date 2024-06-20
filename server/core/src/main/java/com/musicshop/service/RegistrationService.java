package com.musicshop.service;

import com.musicshop.entity.AppUser;
import com.musicshop.error.OccupiedLoginException;
import com.musicshop.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public void signUpCustomer(String username, String password) {
        try {
            userRepo.save(new AppUser(username, passwordEncoder.encode(password), AppUser.Role.CUSTOMER));
        } catch (DataIntegrityViolationException e) {
            throw new OccupiedLoginException("Login " + username + " is occupied", e);
        }
    }
}
