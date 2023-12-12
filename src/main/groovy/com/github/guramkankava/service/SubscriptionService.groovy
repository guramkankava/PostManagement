package com.github.guramkankava.service

import com.github.guramkankava.document.Subscription

interface SubscriptionService {

    Subscription subscribeTo(String username)

    void unsubscribeFrom(String username)

    List<Subscription> mySubscriptions()
}