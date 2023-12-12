package com.github.guramkankava.repository

import com.github.guramkankava.document.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String>, PostRepositoryCustom {

    Optional<Post> findByIdAndUsername(String id, String username)

}