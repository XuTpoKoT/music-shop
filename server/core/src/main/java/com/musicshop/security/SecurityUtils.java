package com.musicshop.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static SecurityUser getSecurityUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
