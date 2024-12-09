package com.palolandrae.undue.repositories

import com.palolandrae.undue.objects.Subscription
import com.palolandrae.undue.objects.SubscriptionDto

interface SubscriptionRepository {
    suspend fun createSubscription(subscription: Subscription): Boolean
    suspend fun getSubscriptions(): List<SubscriptionDto>?
    suspend fun getSubscription(id: String): SubscriptionDto
    suspend fun deleteSubscription(id: String)
    suspend fun updateSubscription(
        id: String, name: String, price: Double
    )
}