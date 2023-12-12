package com.github.guramkankava.document

import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@EqualsAndHashCode(includes = 'id')
@Document
class Comment {

    @Id
    String id

    String content

    String username
}
