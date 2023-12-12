package com.github.guramkankava.repository

import com.github.guramkankava.document.Subscription
import org.springframework.data.mongodb.repository.MongoRepository

interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    Optional<Subscription> findBySubscriberAndPublisher(String subscriber, String publisher)

    List<Subscription> findBySubscriber(String subscriber)
}