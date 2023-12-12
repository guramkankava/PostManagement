package com.github.guramkankava.service

import org.springframework.security.core.Authentication

import org.springframework.security.core.context.SecurityContextHolder

class SecurityContextHolderAuthService implements AuthService {

    @Override
    Authentication getAuthentication() {
        SecurityContextHolder.getContext().getAuthentication();
    }

}
