package com.github.guramkankava.service

import org.springframework.security.core.Authentication

interface TokenService {

    String generateToken(Authentication authentication)

}