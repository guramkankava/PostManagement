package com.github.guramkankava.configuration

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPublicKey

@EnableWebSecurity
@Configuration
class AuthConfiguration {

    public static final String USERNAME = 'john_doe'
    private final KeyPair keyPair

    AuthConfiguration() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA")

        keyPairGenerator.initialize(2048)
        keyPair = keyPairGenerator.generateKeyPair()
    }

    @Order(2)
    @Bean
    SecurityFilterChain oauthSecurityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.
            securityMatcher('/api/v1/**').
            sessionManagement(smc -> {
                smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }).
            csrf(csrfCustomizer -> {
               csrfCustomizer.disable()
            }).
            authorizeHttpRequests(authCustomizer -> {
               authCustomizer.requestMatchers('api/v1/users/register').permitAll()
               authCustomizer.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll();
               authCustomizer.anyRequest().authenticated()
            }).
            oauth2ResourceServer(oauth2RSCustomizer -> {
                oauth2RSCustomizer.jwt(jwtCustomizer -> {
                    jwtCustomizer.decoder(jwtDecoder())
                })
            }).
        build()
    }

    @Order(1)
    @Bean
    SecurityFilterChain httpBasicSecurityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.
                securityMatcher("/auth/token").
                sessionManagement(smc -> {
                    smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }).
                csrf(csrfCustomizer -> {
                    csrfCustomizer.disable()
                }).
                authorizeHttpRequests(authCustomizer -> {
                    authCustomizer.anyRequest().authenticated()
                }).
                httpBasic(httpBasicCustomizer -> {
                    Customizer.withDefaults()
                }).
                build()
    }

    @Bean
    InMemoryUserDetailsManager userDetailsManager() {
        new InMemoryUserDetailsManager(User.withUsername(USERNAME).password('{noop}password').authorities('read').build())
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder.withPublicKey((RSAPublicKey)keyPair.getPublic()).build()
    }

    @Bean
    JwtEncoder jwtEncoder() {
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic()).
                privateKey(keyPair.getPrivate()).
                build()
        JWKSet jwkSet = new JWKSet(rsaKey)
        new NimbusJwtEncoder(new ImmutableJWKSet<>(jwkSet))
    }
}
