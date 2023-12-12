package com.github.guramkankava.controller

import com.github.guramkankava.service.PostLikeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = "/api/v1/likes")
@RestController
class LikeController {

    private final PostLikeService postLikeService

    LikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post/{postId}")
    void likeAPost(@PathVariable("postId") String postId) {
        postLikeService.likeAPost(postId)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{likeId}/post/{postId}")
    void unlikeAPost(@PathVariable(name = 'likeId') String likeId, @PathVariable(name = 'postId') String postId) {
        postLikeService.unlikeAPost(likeId, postId)
    }
}
