package com.github.guramkankava.service

import com.github.guramkankava.document.Post

interface PostService {

    Post get(String id)

    Post getOwn(String id)

    Post addAPost(Post post)

    Post updateAPost(Post post)

    List<Post> getAllPosts()

    void deleteAPost(String postId)
}