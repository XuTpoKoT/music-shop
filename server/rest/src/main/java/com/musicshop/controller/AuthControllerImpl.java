package com.musicshop.controller;

import com.musicshop.dto.request.SignInRequest;
import com.musicshop.dto.request.SignUpRequest;
import com.musicshop.dto.response.JwtAuthenticationResponse;
import com.musicshop.entity.AppUser;
import com.musicshop.service.AuthService;
import com.musicshop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    private final UserService userService;

    @Override
    public JwtAuthenticationResponse signUp(@Valid @RequestBody SignUpRequest req) {
        String jwt = authService.signUp(req);
        AppUser user = userService.findByUsername(req.username());
        return new JwtAuthenticationResponse(jwt, user.getUsername());
    }

    @Override
    public JwtAuthenticationResponse signIn(@Valid @RequestBody SignInRequest request) {
        String jwt = authService.signIn(request);
        AppUser user = userService.findByUsername(request.login());
        return new JwtAuthenticationResponse(jwt, user.getUsername());
    }
}
