package com.musicshop.controller;

import com.musicshop.dto.request.SignInRequest;
import com.musicshop.dto.request.SignUpRequest;
import com.musicshop.dto.response.JwtAuthenticationResponse;
import com.musicshop.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public JwtAuthenticationResponse signUp(@Valid @RequestBody SignUpRequest req) {
        return authService.signUp(req);
    }

    @Override
    public JwtAuthenticationResponse signIn(@Valid @RequestBody SignInRequest request) {
        return authService.signIn(request);
    }
}
