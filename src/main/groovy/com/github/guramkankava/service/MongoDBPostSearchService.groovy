package com.github.guramkankava.service

import com.github.guramkankava.document.Post
import com.github.guramkankava.document.Subscription
import org.springframework.util.StringUtils

import java.util.stream.Collectors

class MongoDBPostSearchService implements PostSearchService {

    private final PostService postService
    private final SubscriptionService subscriptionService

    MongoDBPostSearchService(PostService postService, SubscriptionService subscriptionService) {
        this.postService = postService
        this.subscriptionService = subscriptionService
    }

    @Override
    List<Post> find(PostSearchCriteria criteria) {
        if (criteria.subscription) {
            Set<String> publishersUsernames = subscriptionService.mySubscriptions().
                    stream().
                    map(Subscription::getPublisher).
                    collect(Collectors.toSet())
            postService.getByUsername(publishersUsernames)
        } else if (StringUtils.hasText(criteria.getUsername())) {
            postService.getByUsername(Set.of(criteria.getUsername()))
        } else {
            List.of()
        }
    }
}
