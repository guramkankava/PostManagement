package com.github.guramkankava.service

import org.springframework.security.core.Authentication

interface AuthService {

    Authentication getAuthentication()

}
