package com.github.guramkankava.repository

import com.github.guramkankava.document.Comment
import com.github.guramkankava.document.Like

interface PostRepositoryCustom {

    void addAComment(String postId, Comment comment)

    void likeAPost(String postId, Like like)

    void unlikeAPost(String postId, Like like)

    void deleteAComment(String postId, String commentId)
}