package com.github.guramkankava.service

import com.github.guramkankava.document.Post
import com.github.guramkankava.exception.PostNotFoundException
import com.github.guramkankava.repository.PostRepository
import org.springframework.security.core.Authentication
import spock.lang.Specification;

class MongoDBPostServiceTest extends Specification {


    def "getOwnById should return a post when id and username criteria satisfied" () {
        setup:
        Post expected = new Post()
        expected.setId('1')
        expected.setUsername('john_doe')
        expected.setContent('very cool post')
        PostRepository postRepository = Mock() {
            findByIdAndUsername('1', 'john_doe') >> Optional.of(expected)
        }

        AuthService authService = Mock() {
            getAuthentication() >> Mock(Authentication) {
                getName() >> 'john_doe'
            }
        }

        PostService postService = new MongoDBPostService(postRepository, Mock(CommentService), Mock(LikeService), authService)

        when:
        Post returned = postService.getOwnById('1')

        then:
        returned == expected
    }

    def "getOwnById should throw PostNotFoundException when id and username criteria not satisfied" () {
        setup:
        PostRepository postRepository = Mock() {
            findByIdAndUsername('1', 'jane_doe') >> Optional.empty()
        }

       AuthService authService = Mock() {
            getAuthentication() >> Mock(Authentication) {
                getName() >> 'jane_doe'
            }
        }

        PostService postService = new MongoDBPostService(postRepository, Mock(CommentService), Mock(LikeService), authService)

        when:
        postService.getOwnById('1')

        then:
        thrown(PostNotFoundException)
    }
}