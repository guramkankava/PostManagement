package com.github.guramkankava.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder

import org.springframework.security.oauth2.jwt.JwtEncoderParameters

import java.time.Instant
import java.time.temporal.ChronoUnit

import java.util.stream.Collectors

class JWTTokenService implements TokenService {

    private final JwtEncoder jwtEncoder

    JWTTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder
    }

    @Override
    String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "))

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()
    }
}
