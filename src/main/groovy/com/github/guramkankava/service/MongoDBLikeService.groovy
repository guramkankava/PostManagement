package com.github.guramkankava.service

import com.github.guramkankava.document.Like
import com.github.guramkankava.exception.LikeNotFoundException
import com.github.guramkankava.repository.LikeRepository

class MongoDBLikeService implements LikeService {

    private final LikeRepository likeRepository

    MongoDBLikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository
    }

    @Override
    Like save(Like like) {
        likeRepository.save(like)
    }

    @Override
    Like get(String id) throws LikeNotFoundException {
        likeRepository.findById(id).orElseThrow(() -> new LikeNotFoundException('Like not found ' + id))
    }

    void deleteMultiple(Set<String> ids) {
        likeRepository.deleteAllById (ids)
    }
}
