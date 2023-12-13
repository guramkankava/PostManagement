package com.github.guramkankava.service

import com.github.guramkankava.document.Comment

interface PostCommentService {

    Comment commentOnPost(String postId, Comment comment)

    void deleteAComment(String postId, String commentId)
}