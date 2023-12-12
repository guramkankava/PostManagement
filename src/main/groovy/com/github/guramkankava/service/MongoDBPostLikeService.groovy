package com.github.guramkankava.service

import com.github.guramkankava.document.Like
import com.github.guramkankava.repository.LikeRepository
import com.github.guramkankava.repository.PostRepository

class MongoDBPostLikeService implements PostLikeService {

    private final LikeService likeService

    private final PostRepository postRepository

    MongoDBPostLikeService(LikeService likeService, PostRepository postRepository) {
        this.likeService = likeService
        this.postRepository = postRepository
    }

    @Override
    void likeAPost(String postId) {
        Like like = new Like()
        //TODO insert username from security context
        like.setUsername('john_doe')
        like = likeService.save(like)
        postRepository.likeAPost(postId, like)
    }

    @Override
    void unlikeAPost(String likeId, String postId) {
        postRepository.unlikeAPost(postId, likeService.get(likeId))
    }
}
