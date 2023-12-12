package com.github.guramkankava.controller

import com.github.guramkankava.mapper.CommentMapper
import com.github.guramkankava.request.CommentRequest
import com.github.guramkankava.service.CommentService
import com.github.guramkankava.service.PostCommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = "/api/v1/comments")
@RestController
class CommentController {

    private final CommentMapper commentMapper
    private final PostCommentService postCommentService
    private final CommentService commentService

    CommentController(CommentMapper commentMapper, PostCommentService postCommentService, CommentService commentService) {
        this.commentMapper = commentMapper
        this.postCommentService = postCommentService
        this.commentService = commentService
    }

    @PostMapping(path = "/post/{postId}")
    void addAComment(@PathVariable("postId") String postId, @RequestBody CommentRequest commentRequest) {
        def comment = commentMapper.commentRequestToCommentDocument(commentRequest)
        postCommentService.commentOnPost(postId, comment)
    }

    @PutMapping('/{commentId}')
    void updateAComment(@PathVariable('commentId') String commentId, @RequestBody CommentRequest commentRequest) {
        def comment = commentMapper.commentRequestToCommentDocument(commentRequest)
        commentService.updateAComment(commentId, comment)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping('/{commentId}/post/{postId}')
    void deleteAComment(@PathVariable('commentId') String commentId, @PathVariable('postId') String postId) {
        postCommentService.deleteAComment(postId, commentId)
    }
}
