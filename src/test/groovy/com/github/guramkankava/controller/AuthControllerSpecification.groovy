package com.github.guramkankava.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerSpecification extends Specification {

    @Autowired
    private MockMvc mockMvc

    @WithMockUser(username = 'ali', password = 'baba')
    def "should return access token" () {
        given:
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post('/auth/token').
                with(SecurityMockMvcRequestPostProcessors.httpBasic('ali', 'baba'))
        )
        expect:
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
        resultActions.andReturn().getResponse().getContentAsString()
    }


}