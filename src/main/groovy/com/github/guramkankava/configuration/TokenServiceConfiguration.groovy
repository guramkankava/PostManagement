package com.github.guramkankava.configuration

import com.github.guramkankava.service.JWTTokenService
import com.github.guramkankava.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtEncoder

@Configuration
class TokenServiceConfiguration {

    @Bean
    TokenService jwtTokenService(JwtEncoder jwtEncoder) {
        new JWTTokenService(jwtEncoder)
    }

}
