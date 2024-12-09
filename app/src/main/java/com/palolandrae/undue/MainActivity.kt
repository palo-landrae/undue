package com.palolandrae.undue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.palolandrae.undue.screens.AddSubscriptionDestination
import com.palolandrae.undue.screens.AddSubscriptionScreen
import com.palolandrae.undue.screens.AuthenticationDestination
import com.palolandrae.undue.screens.SignInScreen
import com.palolandrae.undue.screens.SignUpDestination
import com.palolandrae.undue.screens.SignUpScreen
import com.palolandrae.undue.screens.SubscriptionListDestination
import com.palolandrae.undue.screens.SubscriptionListScreen
import com.palolandrae.undue.ui.theme.UndueTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var supabaseClient: SupabaseClient

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UndueTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                Scaffold { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = SubscriptionListDestination.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(SubscriptionListDestination.route) {
                            SubscriptionListScreen(
                                navController = navController
                            )
                        }

                        composable(AuthenticationDestination.route) {
                            SignInScreen(
                                navController = navController
                            )
                        }

                        composable(SignUpDestination.route) {
                            SignUpScreen(
                                navController = navController
                            )
                        }

                        composable(AddSubscriptionDestination.route) {
                            AddSubscriptionScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
