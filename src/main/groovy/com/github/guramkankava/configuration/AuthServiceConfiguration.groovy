package com.github.guramkankava.configuration

import com.github.guramkankava.service.AuthService
import com.github.guramkankava.service.SecurityContextHolderAuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthServiceConfiguration {

    @Bean
    AuthService securityContextHolderAuthService() {
        new SecurityContextHolderAuthService()
    }
}
