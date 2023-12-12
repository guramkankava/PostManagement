package com.github.guramkankava.configuration

import com.github.guramkankava.repository.LikeRepository
import com.github.guramkankava.service.LikeService
import com.github.guramkankava.service.MongoDBLikeService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LikeConfiguration {

    @Bean
    LikeService mongoDbLikeService(LikeRepository likeRepository) {
        new MongoDBLikeService(likeRepository)
    }
}
