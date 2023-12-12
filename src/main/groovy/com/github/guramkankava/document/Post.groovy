package com.github.guramkankava.document

import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@EqualsAndHashCode(includes = 'id')
@Document
class Post {

    @Id
    String id

    String content

    String username

    @DocumentReference
    List<Comment> comments = new ArrayList<>()

    @DocumentReference
    List<Like> likes = new ArrayList<>()

    List<Comment> getComments() {
        return Collections.unmodifiableList(comments)
    }

    void removeAComment(Comment comment) {
        comments.remove(comment)
    }

    void addAComment(Comment comment) {
        comments.add(comment)
    }

    List<Like> getLikes() {
        return Collections.unmodifiableList(likes)
    }

    void removeALike(Like like) {
        likes.remove(like)
    }

    void addALike(Like like) {
        likes.add(like)
    }
}
