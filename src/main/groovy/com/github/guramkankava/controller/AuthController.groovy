package com.github.guramkankava.controller

import com.github.guramkankava.service.TokenService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = '/auth')
@RestController
class AuthController {

    private final TokenService tokenService

    AuthController(TokenService tokenService) {
        this.tokenService = tokenService
    }

    @PostMapping("/token")
    String token(Authentication authentication) {
        return tokenService.generateToken(authentication)
    }

}
