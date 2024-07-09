package com.musicshop.service;

import com.musicshop.entity.AppUser;
import com.musicshop.error.OccupiedLoginException;
import com.musicshop.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public void signUp(String username, String password) {
        try {
            userRepo.save(new AppUser(username, passwordEncoder.encode(password), AppUser.Role.CUSTOMER));
        } catch (DataIntegrityViolationException e) {
            throw new OccupiedLoginException("Login " + username + " is occupied", e);
        }
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String password = args[0];
        System.out.println(password);
        System.out.println(encoder.encode(password));
    }
}
