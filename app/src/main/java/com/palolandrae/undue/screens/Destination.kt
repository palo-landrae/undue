package com.palolandrae.undue.screens

import androidx.navigation.NavType
import androidx.navigation.navArgument


interface Destination {
    val route: String
    val title: String
}


object SubscriptionListDestination : Destination {
    override val route = "Subscription_list"
    override val title = "Subscription List"
}

object SubscriptionDetailsDestination : Destination {
    override val route = "Subscription_details"
    override val title = "Subscription Details"
    const val SubscriptionId = "Subscription_id"
    val arguments = listOf(navArgument(name = SubscriptionId) {
        type = NavType.StringType
    })
    fun createRouteWithParam(SubscriptionId: String) = "$route/${SubscriptionId}"
}

object AddSubscriptionDestination : Destination {
    override val route = "add_Subscription"
    override val title = "Add Subscription"
}

object AuthenticationDestination: Destination {
    override val route = "authentication"
    override val title = "Authentication"
}

object SignUpDestination: Destination {
    override val route = "signup"
    override val title = "Sign Up"
}
