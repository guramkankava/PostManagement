package com.github.guramkankava.service

import com.github.guramkankava.document.Comment

interface PostCommentService {

    void commentOnPost(String postId, Comment comment)

    void deleteAComment(String postId, String commentId)
}