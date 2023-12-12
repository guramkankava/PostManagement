package com.github.guramkankava.configuration;

import com.github.guramkankava.property.MongoDBProperties;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories(basePackages = "com.github.guramkankava.repository")
@Configuration
class MongoDBConfiguration extends AbstractMongoClientConfiguration {

    private final MongoDBProperties mongoDBProperties

    MongoDBConfiguration(MongoDBProperties mongoDBProperties) {
        this.mongoDBProperties = mongoDBProperties
    }

    @Override
    protected String getDatabaseName() {
        return mongoDBProperties.database
    }

    @Override
    MongoClient mongoClient() {
        final ConnectionString connectionString = new ConnectionString(mongoDBProperties.uri)
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString).build()
        MongoClients.create(mongoClientSettings)
    }

    @Override
    Collection<String> getMappingBasePackages() {
        Collections.singleton(mongoDBProperties.mappingBasePackage);
    }

}