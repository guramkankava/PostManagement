package com.github.guramkankava.controller

import com.github.guramkankava.request.UserRequest
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = 'api/v1/users')
@RestController
class UserController {

    private final InMemoryUserDetailsManager userDetailsManager

    UserController(InMemoryUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager
    }

    @PostMapping(path = '/register')
    void addAUser(@RequestBody UserRequest userRequest) {
        userDetailsManager.createUser(User.withUsername(userRequest.getUsername()).
                password('{noop}' + userRequest.getPassword()).
                authorities('user').
                build()
        )
    }
}
