package com.github.guramkankava.configuration


import com.github.guramkankava.service.MongoDBPostSearchService
import com.github.guramkankava.service.PostSearchService
import com.github.guramkankava.service.PostService
import com.github.guramkankava.service.SubscriptionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PostSearchServiceConfiguration {

    @Bean
    PostSearchService mongoDbPostSearchService(PostService postService, SubscriptionService subscriptionService) {
        new MongoDBPostSearchService(postService, subscriptionService)
    }
}
