package com.palolandrae.undue.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.palolandrae.undue.objects.Subscription
import com.palolandrae.undue.viewmodels.SubscriptionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscriptionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val name = viewModel.name.collectAsState(initial = "")
    val price = viewModel.price.collectAsState(initial = "")
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Subscription") },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Create a new subscription",
                style = MaterialTheme.typography.titleMedium
            )

            // Subscription Name Input
            OutlinedTextField(
                label = {
                    Text(
                        text = "Name",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                maxLines = 1,
                shape = RoundedCornerShape(32),
                modifier = modifier.fillMaxWidth(),
                value = name.value,
                onValueChange = {
                    viewModel.onNameChange(it)
                },
            )

            OutlinedTextField(
                label = {
                    Text(
                        text = "Price",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                maxLines = 1,
                shape = RoundedCornerShape(32),
                modifier = modifier.fillMaxWidth(),
                value = price.value,
                onValueChange = {
                    viewModel.onPriceChange(it)
                },
            )

            // Add Subscription Button
            Button(
                onClick = {
                    val subscription = Subscription(
                        name = name.value,
                        price = price.value.toDoubleOrNull() ?: 0.0,
                    )

                    viewModel.addSubscription(subscription)

                    Toast.makeText(context, "Subscription Added!", Toast.LENGTH_SHORT).show()

                    // Clear input fields
                    viewModel.onNameChange("")
                    viewModel.onPriceChange("")
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Subscription")
            }
        }
    }
}