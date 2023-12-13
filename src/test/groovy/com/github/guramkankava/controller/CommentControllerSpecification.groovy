package com.github.guramkankava.controller

import com.github.guramkankava.document.Comment
import com.github.guramkankava.document.Post
import com.jayway.jsonpath.JsonPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerSpecification extends Specification {

    public static final String URL_POST = '/api/v1/posts'
    public static final String URL_COMMENTS_BASE = '/api/v1/comments'
    public static final String POST_JSON_STRING = '{ "content" : "Very cool post" }'
    public static final String COMMENT_INITIAL_CONTENT = 'I think it is indeed very cool post'
    public static final String COMMENT_CONTENT_V2 = 'I think it is indeed very cool post v2'
    public static final String ERROR_MESSAGE = 'Comment can be deleted by Post or Comment submitter'

    @Autowired
    private MockMvc mockMvc
    @Autowired
    private MongoTemplate mongoTemplate

    def "should add a comment" () {
        setup:
        deletePostsAndComments()
        String accessToken = getAccessToken()
        String postId = postAPost(accessToken)
        when:
        ResultActions resultActions = commentOnAPost(postId, accessToken)
        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated()).
        andExpect(MockMvcResultMatchers.jsonPath('$.id').isNotEmpty()).
        andExpect(MockMvcResultMatchers.jsonPath('$.content').value(COMMENT_INITIAL_CONTENT))
    }

    def "should update a comment" () {
        setup:
        deletePostsAndComments()
        String accessToken = getAccessToken()
        String postId = postAPost(accessToken)
        String commentJson = commentOnAPost(postId, accessToken).
        andReturn().
        getResponse().
        getContentAsString()
        String commentId = JsonPath.read(commentJson, '$.id')
        when:
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(URL_COMMENTS_BASE + "/${commentId}").
                contentType(MediaType.APPLICATION_JSON).
                header('Authorization', 'Bearer ' + accessToken).
                content('{ "content" : "I think it is indeed very cool post v2"}')
        )
        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).
            andExpect(MockMvcResultMatchers.jsonPath('$.id').value(commentId)).
            andExpect (MockMvcResultMatchers.jsonPath('$.content').value(COMMENT_CONTENT_V2))

    }

    def "should delete a comment" () {
        setup:
        deletePostsAndComments()
        String accessToken = getAccessToken()
        String postId = postAPost(accessToken)
        String commentJson = commentOnAPost(postId, accessToken).
                andReturn().
                getResponse().
                getContentAsString()
        String commentId = JsonPath.read(commentJson, '$.id')
        when:
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(URL_COMMENTS_BASE + "/${commentId}/post/${postId}").
                header('Authorization', 'Bearer ' + accessToken)
        )
        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    def "should not delete a comment due to not a submitter" () {
        setup:
        deletePostsAndComments()
        String accessToken = getAccessToken()
        String postId = postAPost(accessToken)
        String commentJson = commentOnAPost(postId, accessToken).
                andReturn().
                getResponse().
                getContentAsString()
        String commentId = JsonPath.read(commentJson, '$.id')
        when:
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(URL_COMMENTS_BASE + "/${commentId}/post/${postId}").
                with(SecurityMockMvcRequestPostProcessors.jwt().jwt(jwt -> jwt.subject('ali')))
        ).andDo(MockMvcResultHandlers.print())
        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isForbidden()).
            andExpect(MockMvcResultMatchers.jsonPath('$.message').value(ERROR_MESSAGE))
    }

    private ResultActions commentOnAPost(String postId, String accessToken) {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_COMMENTS_BASE + '/post/' + postId).
                content('{"content" : "I think it is indeed very cool post"}').
                contentType(MediaType.APPLICATION_JSON).
                header('Authorization', 'Bearer ' + accessToken)
        )
    }

    private String getAccessToken() {
        mockMvc.perform(MockMvcRequestBuilders.post('/auth/token').
                with(SecurityMockMvcRequestPostProcessors.httpBasic('john_doe', 'password'))).
                andReturn().
                getResponse().
                getContentAsString()
    }

    private String postAPost(String accessToken) {
        String postJson = mockMvc.perform(MockMvcRequestBuilders.post(URL_POST).
                content(POST_JSON_STRING).
                contentType(MediaType.APPLICATION_JSON).
                header('Authorization', 'Bearer ' + accessToken)).
                andReturn().
                getResponse().
                getContentAsString()
        JsonPath.read(postJson, '$.id')
    }

    private void deletePostsAndComments() {
        mongoTemplate.remove(new Query(), Post)
        mongoTemplate.remove(new Query(), Comment)
    }
}