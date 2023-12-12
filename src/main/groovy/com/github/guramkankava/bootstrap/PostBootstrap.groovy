package com.github.guramkankava.bootstrap

import com.github.guramkankava.document.Comment
import com.github.guramkankava.document.Like
import com.github.guramkankava.document.Post
import com.github.guramkankava.service.PostCommentService
import com.github.guramkankava.service.PostLikeService
import com.github.guramkankava.service.PostService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class PostBootstrap implements ApplicationRunner{


    public static final String POST_CONTENT = 'Groovy is groovy'
    public static final String COMMENT_CONTENT = 'nice post'
    public static final String USERNAME = 'john_doe'

    private final PostService postService

    private final PostCommentService postCommentService

    private final PostLikeService postLikeService

    private final MongoTemplate mongoTemplate

    PostBootstrap(PostService postService, PostCommentService postCommentService, PostLikeService postLikeService, MongoTemplate mongoTemplate) {
        this.postService = postService
        this.postCommentService = postCommentService
        this.postLikeService = postLikeService
        this.mongoTemplate = mongoTemplate
    }

    @Override
    void run(ApplicationArguments args) throws Exception {
        deletePosts()
        def post = new Post()
        post.setContent( POST_CONTENT)
        post.setUsername(USERNAME)

        def comment = new Comment()
        comment.setContent(COMMENT_CONTENT)

        mongoTemplate.save(comment)
        Like like = mongoTemplate.save(new Like())

        post.addAComment(comment)
        post.addALike(like)
        mongoTemplate.save(post)
    }

    private void deletePosts() {
        Query deleteAllLikesQuery = new Query().addCriteria(Criteria.where('username').is(USERNAME))
        mongoTemplate.findAllAndRemove(deleteAllLikesQuery, Like)

        Query deleteAllComments = new Query().addCriteria(Criteria.where('content').is(COMMENT_CONTENT))
        mongoTemplate.findAllAndRemove(deleteAllComments, Comment)

        Query deleteAllPostsQuery = new Query().addCriteria(Criteria.where('content').is(POST_CONTENT))
        mongoTemplate.findAllAndRemove(deleteAllPostsQuery, Post)
    }
}
