package com.github.guramkankava.service

import com.github.guramkankava.document.Post

interface PostSearchService {
    List<Post> find(PostSearchCriteria criteria)

    static class PostSearchCriteria {
        boolean subscription
        String username
    }
}
