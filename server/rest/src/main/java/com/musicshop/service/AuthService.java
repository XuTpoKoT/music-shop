package com.musicshop.service;

import com.musicshop.security.SecurityUser;
import com.musicshop.dto.request.SignInRequest;
import com.musicshop.dto.request.SignUpRequest;
import com.musicshop.dto.response.JwtAuthenticationResponse;
import com.musicshop.entity.AppUser;
import com.musicshop.error.AccessForbiddenException;
import com.musicshop.error.OccupiedLoginException;
import com.musicshop.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest req) {
        try {
            AppUser appUser = new AppUser(req.username(), passwordEncoder.encode(req.password()),
                    AppUser.Role.CUSTOMER);
            userRepo.save(appUser);
            return generateToken(appUser);
        } catch (DataIntegrityViolationException e) {
            throw new OccupiedLoginException("Login " + req.username() + " is occupied", e);
        }
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        log.info("Sign in called with login " + request.login());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.login(),
                request.password()
        ));
        log.info("End authenticationManager.authenticate");
        AppUser appUser = userRepo.findByUsername(request.login()).orElseThrow(AccessForbiddenException::new);
        return generateToken(appUser);
    }

    private JwtAuthenticationResponse generateToken(AppUser appUser) {
        SecurityUser securityUser = new SecurityUser(appUser);
        Map<String, Object> claims = Map.of(
                "username", appUser.getUsername(),
                "role", appUser.getRole()
        );
        log.info("Start gen token");
        String jwt = jwtService.generateToken(securityUser, claims);
        return new JwtAuthenticationResponse(jwt);
    }
}
