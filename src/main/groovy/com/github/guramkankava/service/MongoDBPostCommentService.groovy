package com.github.guramkankava.service

import com.github.guramkankava.document.Comment
import com.github.guramkankava.exception.ForbiddenException
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
    Comment commentOnPost(String postId, Comment comment) {
        comment.setUsername(authService.getAuthentication().getName())
        comment =  commentRepository.save(comment)
        postRepository.addAComment(postId, comment)
        comment
    }

    @Override
    void deleteAComment(String postId, String commentId) {
        if(canDelete(postId, commentId)) {
            postRepository.deleteAComment(postId, commentId)
        } else {
            throw new ForbiddenException('Comment can be deleted by Post or Comment submitter')
        }
    }

    private boolean canDelete(String postId, String commentId) {
        String username = authService.getAuthentication().getName()
        (commentRepository.findByIdAndUsername(commentId, username).isPresent() || postRepository.findByIdAndUsername(postId, username).isPresent())
    }

}
