package com.github.guramkankava.document

import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@EqualsAndHashCode(includes = ['id','username'])
@Document
class User {

    @Id
    String id

    @Indexed(unique = true)
    String username

    String password

}
