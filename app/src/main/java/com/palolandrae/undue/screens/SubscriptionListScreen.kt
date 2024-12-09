package com.palolandrae.undue.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.palolandrae.undue.viewmodels.SubscriptionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SubscriptionViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getSubscriptions()
    }
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Subscriptions"
                    )
                },
            )
        },
        floatingActionButton = {
            AddSubscriptionButton(onClick = { navController.navigate(AddSubscriptionDestination.route) })
        }
    ){ padding ->
        val subscriptionList = viewModel.subscriptionList.collectAsState(initial = listOf()).value
        if (!subscriptionList.isNullOrEmpty()) {
            LazyColumn(
                modifier = modifier.padding(padding),
                contentPadding = PaddingValues(5.dp)
            ) {
                items(subscriptionList) { subscription ->
                    ListItem(
                        headlineContent = { Text(text = "${subscription.name} (€ ${"%.2f".format(subscription.price)})" ) },
                    )
                }
            }
        } else {
            Text("Product list is empty!")
        }
    }
}


@Composable
private fun AddSubscriptionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
        )
    }
}
