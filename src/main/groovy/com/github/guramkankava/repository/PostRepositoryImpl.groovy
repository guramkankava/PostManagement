package com.github.guramkankava.repository

import com.github.guramkankava.document.Comment
import com.github.guramkankava.document.Like
import com.github.guramkankava.document.Post
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Update

class PostRepositoryImpl implements PostRepositoryCustom {

    private final MongoTemplate mongoTemplate

    PostRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate
    }

    @Override
    void addAComment(String postId, Comment comment) {
        mongoTemplate.update(Post).
        matching(Criteria.where("id").is(postId)).
        apply(new Update().push("comments", comment)).first()
    }

    @Override
    void deleteAComment(String postId, String commentId) {
        Optional.of(mongoTemplate.findById(postId, Post)).
        ifPresent {
            Comment comment = new Comment()
            comment.setId(commentId)
            it.removeAComment(comment)
            mongoTemplate.remove(comment)
            mongoTemplate.save(it)
        }
    }



    @Override
    void likeAPost(String postId, Like like) {
        mongoTemplate.update(Post).
        matching(Criteria.where("id").is(postId)).
        apply(new Update().push("likes", like)).first()
    }

    @Override
    void unlikeAPost(String postId, Like like) {
        Optional.of(mongoTemplate.findById(postId, Post)).ifPresent(post -> {
            post.removeALike(like)
            mongoTemplate.remove(like)
            mongoTemplate.save(post)
        })
    }
}
