package com.github.guramkankava.service

import com.github.guramkankava.document.Like
import com.github.guramkankava.exception.LikeNotFoundException

interface LikeService {

    Like save(Like like)
    Like get(String id) throws LikeNotFoundException
    void deleteMultiple(Set<String> ids)

}