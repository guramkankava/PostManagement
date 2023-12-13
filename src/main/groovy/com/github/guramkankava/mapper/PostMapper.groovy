package com.github.guramkankava.mapper

import com.github.guramkankava.request.PostRequest
import com.github.guramkankava.document.Post
import com.github.guramkankava.response.PostResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface PostMapper {

    @Mapping(target = "metaClass", ignore = true)
    Post postRequestToPostDocument(PostRequest postRequest)

    @Mapping(target = "metaClass", ignore = true)
    PostResponse postDocumentToPostResponse(Post post)
}