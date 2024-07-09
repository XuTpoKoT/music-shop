package com.musicshop.controller;

import com.musicshop.dto.request.SignUpRequest;
import com.musicshop.error.OccupiedLoginException;
import com.musicshop.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/${api-version}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    @Value("${api-version}")
    private String apiVersion;
    private final RegistrationService registrationService;

    @GetMapping("/sign-in")
    public String getSignInPage() {
        log.info("getSignInPage called");
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String getSignUpPage(@ModelAttribute("signUpRequest") SignUpRequest req) {
        log.info("getSignUpPage called");
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("signUpRequest") SignUpRequest req, BindingResult bindingResult,
                         Model model) {
        log.info("signUp called with username " + req.username());
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        try {
            registrationService.signUp(req.username(), req.password());
        } catch (OccupiedLoginException e) {
            log.info(e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "sign-up";
        }

        return "redirect:/" + apiVersion + "/products";
    }
}
