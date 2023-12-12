package com.github.guramkankava.controller

import com.github.guramkankava.request.PostRequest
import com.github.guramkankava.document.Post
import com.github.guramkankava.mapper.PostMapper
import com.github.guramkankava.response.PostResponse
import com.github.guramkankava.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import java.util.stream.Collectors

@RequestMapping("/api/v1/posts")
@RestController
class PostController {

    private final PostMapper postMapper
    private final PostService postService

    PostController(PostMapper postMapper, PostService postService) {
        this.postMapper = postMapper
        this.postService = postService
    }

    @GetMapping
    List<PostResponse> getPosts() {
        postService.getAllPosts().stream().
                map {postMapper.postDocumentToPostResponse(it)}.
                collect(Collectors.toList())
    }

    @PostMapping
    PostResponse postAPost(@RequestBody PostRequest postRequest) {
        Post post = postMapper.postRequestToPostDocument(postRequest)
        postService.addAPost post
        postMapper.postDocumentToPostResponse post
    }

    @PutMapping
    PostResponse updateAPost(@RequestBody PostRequest postRequest) {
        Post post = postMapper.postRequestToPostDocument(postRequest)
        postService.updateAPost(post)
        postMapper.postDocumentToPostResponse post
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = '/{postId}')
    void deleteAPost(@PathVariable(name = 'postId') String postId) {
        postService.deleteAPost(postId)
    }
}