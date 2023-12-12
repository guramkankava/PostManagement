package com.github.guramkankava.repository

import com.github.guramkankava.document.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, String> {

}