package com.github.guramkankava.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification


@AutoConfigureMockMvc
@SpringBootTest
class UserControllerSpecification extends Specification {

    @Autowired
    private MockMvc client

    @WithMockUser
    def "should register a user" () {
        given:
        ResultActions resultActions = client.perform (MockMvcRequestBuilders.post('/api/v1/users/register').
                contentType(MediaType.APPLICATION_JSON).
                content(' { "username" : "ali", "password" : "baba" } ')
        )
        expect:
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
    }
}