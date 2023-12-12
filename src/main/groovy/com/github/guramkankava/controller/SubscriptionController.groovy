package com.github.guramkankava.controller

import com.github.guramkankava.mapper.SubscriptionMapper
import com.github.guramkankava.response.SubscriptionResponse
import com.github.guramkankava.service.SubscriptionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import java.util.stream.Collectors

@RequestMapping(path = 'api/v1/subscriptions')
@RestController
class SubscriptionController {

    private final SubscriptionService subscriptionService
    private final SubscriptionMapper subscriptionMapper

    SubscriptionController(SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper) {
        this.subscriptionService = subscriptionService
        this.subscriptionMapper = subscriptionMapper
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = '/{username}')
    void subscribeAUser(@PathVariable(name = 'username') String username) {
        subscriptionService.subscribeTo(username)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = '/{username}')
    void unsubscribeAUser(@PathVariable(name = 'username') String username) {
        subscriptionService.unsubscribeFrom(username)
    }

    @GetMapping
    List<SubscriptionResponse> mySubscriptions() {
        subscriptionService.mySubscriptions().stream().
        map(subscriptionMapper::subscriptionDocumentToSubscriptionResponse).
        collect(Collectors.toList())
    }
}
