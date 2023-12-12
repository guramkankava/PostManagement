package com.github.guramkankava.service

import com.github.guramkankava.document.Like

interface PostLikeService {

    void likeAPost(String postId)

    void unlikeAPost(String likeId, String postId)

}