package com.github.guramkankava.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.guramkankava.document.Post
import com.github.guramkankava.request.PostRequest
import com.jayway.jsonpath.JsonPath
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import spock.lang.Specification;


@AutoConfigureMockMvc
@SpringBootTest
class PostControllerSpecification extends Specification {

    public static final String URL_POST = '/api/v1/posts'
    public static final String POST_JSON_STRING = '{ "content" : "Very cool post" }'
    public static final String POST_INITIAL_CONTENT = 'Very cool post'
    public static final String POST_UPDATED_CONTENT = 'Very cool post has been updated'
    @Autowired
    private MockMvc mockMvc

    @Autowired
    private MongoTemplate mongoTemplate

    def "should add a post"() {
        setup:
        deletePosts()
        String accessToken = getAccessToken()
        when:
        ResultActions resultActions = postAPost(accessToken)
        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated()).
                andExpect(MockMvcResultMatchers.jsonPath('$.id').isNotEmpty()).
                andExpect(MockMvcResultMatchers.jsonPath('$.content').value(POST_INITIAL_CONTENT)).
                andExpect(MockMvcResultMatchers.jsonPath('$.username').value('john_doe'))
    }

    def "should update a post"() {
        setup:
        deletePosts()
        String accessToken = getAccessToken()
        String responseContent = postAPost(accessToken).
                andReturn().
                getResponse().
                getContentAsString()
        String postId = JsonPath.read(responseContent, '$.id')

        PostRequest postRequest = new PostRequest()
        postRequest.setId(postId)
        postRequest.setContent(POST_UPDATED_CONTENT)
        when:
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(URL_POST).
                        content(objectToJsonString(postRequest)).
                        contentType(MediaType.APPLICATION_JSON).
                        header('Authorization', 'Bearer ' + accessToken))
        then:
             resultActions.andExpect(MockMvcResultMatchers.status().isCreated()).
                andExpect(MockMvcResultMatchers.jsonPath('$.id').value(postId)).
                andExpect(MockMvcResultMatchers.jsonPath('$.content').value(POST_UPDATED_CONTENT)).
                andExpect(MockMvcResultMatchers.jsonPath('$.username').value('john_doe'))
    }

    def "should delete a post"() {
        setup:
        deletePosts()
        String accessToken = getAccessToken()
        String responseContent = postAPost(accessToken).
        andReturn().
        getResponse().
        getContentAsString()
        String postId = JsonPath.read(responseContent, '$.id')
        when:
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(URL_POST+'/'+postId).
                header('Authorization', 'Bearer ' + accessToken)
        )
        then:
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    def "should get a post by username"() {
        setup:
        deletePosts()
        String accessToken = getAccessToken()
        postAPost(accessToken)
        when:
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.get(URL_POST).
                header('Authorization', 'Bearer ' + accessToken).
                param('username', 'john_doe')
        )
        then:
        resultAction.andExpect(MockMvcResultMatchers.status().isOk()).
        andExpect(MockMvcResultMatchers.jsonPath('$[*].id').isNotEmpty()).
        andExpect(MockMvcResultMatchers.jsonPath('$[*].content').value(POST_INITIAL_CONTENT)).
        andExpect(MockMvcResultMatchers.jsonPath('$[*].username').value('john_doe'))
    }

    private ResultActions postAPost(String accessToken) {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_POST).
                content(POST_JSON_STRING).
                contentType(MediaType.APPLICATION_JSON).
                header('Authorization', 'Bearer ' + accessToken))
    }

    private DeleteResult deletePosts() {
        mongoTemplate.remove(new Query(), Post)
    }

    private String getAccessToken() {
        mockMvc.perform(MockMvcRequestBuilders.post('/auth/token').
                with(SecurityMockMvcRequestPostProcessors.httpBasic('john_doe', 'password'))).
                andReturn().
                getResponse().
                getContentAsString()
    }

    private String objectToJsonString( Object obj ) throws JsonProcessingException, JsonMappingException {
        return new ObjectMapper().writeValueAsString(obj)
    }
}