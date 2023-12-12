package com.github.guramkankava.configuration

import com.github.guramkankava.repository.CommentRepository
import com.github.guramkankava.service.CommentService
import com.github.guramkankava.service.MongoDBCommentService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommentConfiguration {

    @Bean
    CommentService mongoDbCommentService(CommentRepository commentRepository) {
        new MongoDBCommentService(commentRepository)
    }
}
