package com.github.guramkankava.mapper

import com.github.guramkankava.document.Comment
import com.github.guramkankava.request.CommentRequest
import com.github.guramkankava.response.CommentResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CommentMapper {

    @Mapping(target = "metaClass", ignore = true)
    Comment commentRequestToCommentDocument(CommentRequest commentRequest)

    @Mapping(target = "metaClass", ignore = true)
    CommentResponse commentDocumentToCommentResponse(Comment comment)

}