package com.github.guramkankava.document

import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@EqualsAndHashCode
@Document
class Subscription {

    @Id
    String id

    String subscriber

    String publisher

}
