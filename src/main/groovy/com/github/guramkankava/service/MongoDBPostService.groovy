package com.github.guramkankava.service

import com.github.guramkankava.document.Comment
import com.github.guramkankava.document.Like
import com.github.guramkankava.document.Post
import com.github.guramkankava.repository.PostRepository
import com.github.guramkankava.exception.PostNotFoundException

import java.util.stream.Collectors

class MongoDBPostService implements PostService {

    private final PostRepository postRepository
    private final CommentService commentService
    private final LikeService likeService
    private final AuthService authService

    MongoDBPostService(PostRepository postRepository, CommentService commentService, LikeService likeService, AuthService authService) {
        this.postRepository = postRepository
        this.commentService = commentService
        this.likeService = likeService
        this.authService = authService
    }

    @Override
    List<Post> getOwn() {
        postRepository.findByUsername(authService.getAuthentication().getName())
    }

    @Override
    Post getOwnById(String id) {
        postRepository.findByIdAndUsername(id, authService.getAuthentication().getName()).
            orElseThrow(() -> new PostNotFoundException("Post not found " + id))
    }

    @Override
    Post addAPost(Post post) {
        post.setUsername(authService.getAuthentication().getName())
        return postRepository.save(post)
    }

    @Override
    Post updateAPost(Post post) {
        getOwnById(post.getId())
        postRepository.save(post)
    }

    @Override
    List<Post> getByUsername(Set<String> usernames) {
        postRepository.findByUsernameIn(usernames)
    }

    @Override
    void deleteAPost(String postId) {
        Post post = getOwnById(postId)
        commentService.deleteMultiple(post.comments.stream().map(Comment::getId).collect(Collectors.toSet()))
        likeService.deleteMultiple(post.likes.stream().map(Like::getId).collect(Collectors.toSet()))
        postRepository.delete(post)
    }
}
