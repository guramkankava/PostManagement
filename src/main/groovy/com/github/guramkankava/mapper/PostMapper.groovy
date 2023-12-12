package com.github.guramkankava.mapper

import com.github.guramkankava.request.PostRequest
import com.github.guramkankava.document.Post
import com.github.guramkankava.response.PostResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PostMapper {

    Post postRequestToPostDocument(PostRequest postRequest)

    PostResponse postDocumentToPostResponse(Post post)
}