package com.github.guramkankava.service

import com.github.guramkankava.document.Comment
import com.github.guramkankava.exception.CommentNotFound
import com.github.guramkankava.repository.CommentRepository

class MongoDBCommentService implements CommentService {

    private final CommentRepository commentRepository

    MongoDBCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository
    }

    @Override
    Comment getAComment(String commentId) {
        commentRepository.findById(commentId).orElseThrow {new CommentNotFound('Comment not found' + commentId)}
    }

    @Override
    Comment updateAComment(String commentId, Comment comment) {
        getAComment(commentId)
        comment.setId(commentId)
        commentRepository.save(comment)
    }

    @Override
    void deleteMultiple(Set<String> ids) {
        commentRepository.deleteAllById(ids)
    }
}
