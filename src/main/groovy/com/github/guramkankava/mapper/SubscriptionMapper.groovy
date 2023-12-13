package com.github.guramkankava.mapper

import com.github.guramkankava.document.Subscription
import com.github.guramkankava.response.SubscriptionResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface SubscriptionMapper {

    @Mapping(target = "metaClass", ignore = true)
    SubscriptionResponse subscriptionDocumentToSubscriptionResponse(Subscription subscription);

}