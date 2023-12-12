package com.github.guramkankava.configuration

import com.github.guramkankava.repository.SubscriptionRepository
import com.github.guramkankava.service.AuthService
import com.github.guramkankava.service.MongoDBSubscriptionService
import com.github.guramkankava.service.SubscriptionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SubscriptionConfiguration {

    @Bean
    SubscriptionService mongoDbSubscriptionService(AuthService authService, SubscriptionRepository subscriptionRepository) {
        new MongoDBSubscriptionService(authService, subscriptionRepository)
    }

}
