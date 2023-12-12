package com.github.guramkankava.repository

import com.github.guramkankava.document.Like
import org.springframework.data.mongodb.repository.MongoRepository

interface LikeRepository extends MongoRepository<Like, String> {

}