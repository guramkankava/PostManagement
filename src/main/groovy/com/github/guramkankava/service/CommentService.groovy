package com.github.guramkankava.service

import com.github.guramkankava.document.Comment

interface CommentService {
    Comment getOwnComment(String id)
    Comment updateAComment(String commentId, Comment comment)
    void deleteMultiple(Set<String> ids)
}
