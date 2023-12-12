package com.github.guramkankava.configuration

import com.github.guramkankava.repository.CommentRepository
import com.github.guramkankava.repository.PostRepository
import com.github.guramkankava.service.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PostConfiguration {

    @Bean
    PostService mongoDBPostService(PostRepository postRepository, CommentService commentService, LikeService likeService, AuthService authService) {
        new MongoDBPostService(postRepository, commentService, likeService, authService)
    }

    @Bean
    PostCommentService mongoDbPostCommentService(PostRepository postRepository, CommentRepository commentRepository, AuthService authService) {
        new MongoDBPostCommentService(postRepository, commentRepository, authService)
    }

    @Bean
    PostLikeService mongoDbPostLikeService(LikeService likeService, PostRepository postRepository) {
        new MongoDBPostLikeService(likeService, postRepository)
    }
}
