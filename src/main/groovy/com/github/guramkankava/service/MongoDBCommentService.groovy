package com.github.guramkankava.service

import com.github.guramkankava.document.Comment
import com.github.guramkankava.exception.CommentNotFoundException
import com.github.guramkankava.repository.CommentRepository

class MongoDBCommentService implements CommentService {

    private final CommentRepository commentRepository
    private final AuthService authService

    MongoDBCommentService(CommentRepository commentRepository, AuthService authService) {
        this.commentRepository = commentRepository
        this.authService = authService
    }

    @Override
    Comment getOwnComment(String id) {
        commentRepository.findByIdAndUsername(id, authService.getAuthentication().getName()).
        orElseThrow(() -> new CommentNotFoundException("Comment not found ${id}"))
    }

    @Override
    Comment updateAComment(String commentId, Comment comment) {
        getOwnComment(commentId)
        comment.setId(commentId)
        comment.setUsername(authService.getAuthentication().getName())
        commentRepository.save(comment)
    }

    @Override
    void deleteMultiple(Set<String> ids) {
        commentRepository.deleteAllById(ids)
    }
}
