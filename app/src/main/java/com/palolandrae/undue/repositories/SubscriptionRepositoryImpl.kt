package com.palolandrae.undue.repositories

import com.palolandrae.undue.objects.Subscription
import com.palolandrae.undue.objects.SubscriptionDto
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : SubscriptionRepository {
    override suspend fun createSubscription(subscription: Subscription): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val subscriptionDto = SubscriptionDto(
                    name = subscription.name,
                    price = subscription.price,
                )
                postgrest.from("Subscriptions").insert(subscriptionDto)
                true
            }
            true
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSubscriptions(): List<SubscriptionDto> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Subscriptions")
                .select().decodeList<SubscriptionDto>()
            result
        }
    }


    override suspend fun getSubscription(id: String): SubscriptionDto {
        return withContext(Dispatchers.IO) {
            postgrest.from("Subscriptions").select {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<SubscriptionDto>()
        }
    }

    override suspend fun deleteSubscription(id: String) {
        return withContext(Dispatchers.IO) {
            postgrest.from("Subscriptions").delete {
                filter {
                    eq("id", id)
                }
            }
        }
    }

    override suspend fun updateSubscription(
        id: String,
        name: String,
        price: Double
    ) {
        withContext(Dispatchers.IO) {
            postgrest.from("Subscriptions").update({
                set("name", name)
                set("price", price)
            }) {
                filter {
                    eq("id", id)
                }
            }
        }
    }
}
