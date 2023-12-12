package com.github.guramkankava.mapper

import com.github.guramkankava.document.Subscription
import com.github.guramkankava.response.SubscriptionResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface SubscriptionMapper {

    SubscriptionResponse subscriptionDocumentToSubscriptionResponse(Subscription subscription);

}