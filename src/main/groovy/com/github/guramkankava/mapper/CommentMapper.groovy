package com.github.guramkankava.mapper

import com.github.guramkankava.document.Comment
import com.github.guramkankava.request.CommentRequest
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CommentMapper {

    Comment commentRequestToCommentDocument(CommentRequest commentRequest)

}