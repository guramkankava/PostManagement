package com.github.guramkankava.service

import com.github.guramkankava.document.Comment
import com.github.guramkankava.repository.CommentRepository
import com.github.guramkankava.repository.PostRepository

class MongoDBPostCommentService implements PostCommentService {

    private final PostRepository postRepository
    private final CommentRepository commentRepository
    private final AuthService authService

    MongoDBPostCommentService(PostRepository postRepository, CommentRepository commentRepository, AuthService authService) {
        this.postRepository = postRepository
        this.commentRepository = commentRepository
        this.authService = authService
    }

    @Override
    void commentOnPost(String postId, Comment comment) {
        comment.setUsername(authService.getAuthentication().getName())
        comment =  commentRepository.save(comment)
        postRepository.addAComment(postId, comment)
    }

    @Override
    void deleteAComment(String postId, String commentId) {
        postRepository.deleteAComment(postId, commentId)
    }
}
