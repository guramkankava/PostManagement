package com.github.guramkankava.service

import com.github.guramkankava.document.Subscription
import com.github.guramkankava.repository.SubscriptionRepository

class MongoDBSubscriptionService implements SubscriptionService {

    private final AuthService authService

    private final SubscriptionRepository subscriptionRepository

    MongoDBSubscriptionService(AuthService authService, SubscriptionRepository subscriptionRepository) {
        this.authService = authService
        this.subscriptionRepository = subscriptionRepository
    }

    @Override
    Subscription subscribeTo(String username) {
        String subscriber = authService.getAuthentication().getName()
        subscriptionRepository.findBySubscriberAndPublisher(subscriber, username).orElseGet(() -> {
            Subscription subscription = new Subscription()
            subscription.setSubscriber(subscriber)
            subscription.setPublisher(username)
            subscriptionRepository.save(subscription)
        })
    }

    @Override
    void unsubscribeFrom(String username) {
        String subscriber = authService.getAuthentication().getName()
        subscriptionRepository.findBySubscriberAndPublisher(subscriber, username).ifPresent(subscription -> {
            subscriptionRepository.delete(subscription)
        })
    }

    @Override
    List<Subscription> mySubscriptions() {
        subscriptionRepository.findBySubscriber(authService.getAuthentication().getName())
    }
}
