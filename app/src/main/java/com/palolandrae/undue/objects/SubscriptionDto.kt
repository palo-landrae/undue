package com.palolandrae.undue.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionDto(
    @SerialName("name")
    val name: String,

    @SerialName("price")
    val price: Double,

    @SerialName("id")
    val id: String = "",
)
