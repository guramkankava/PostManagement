package com.github.guramkankava.service

import com.github.guramkankava.document.Post

interface PostService {

    List<Post> getOwn()

    Post getOwnById(String id)

    Post addAPost(Post post)

    Post updateAPost(Post post)

    List<Post> getByUsername(Set<String> usernames)

    void deleteAPost(String postId)
}