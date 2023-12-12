package com.github.guramkankava.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@ConfigurationProperties(prefix = "spring.data.mongodb")
@Component
class MongoDBProperties {
    String uri
    String database
    String mappingBasePackage
}
