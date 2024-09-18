package com.kotlinroid.eventease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlinroid.eventease.composables.ForgotPassword
import com.kotlinroid.eventease.composables.Login
import com.kotlinroid.eventease.composables.Register
import com.kotlinroid.eventease.composables.WelcomeScreen
import com.kotlinroid.eventease.ui.theme.EventEaseTheme
import com.kotlinroid.eventease.ui.theme.ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ViewModel = viewModel()
            EventEaseTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: ViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome_screen") {
        composable("welcome_screen") {
            WelcomeScreen(navController, viewModel)
        }
        composable("login_screen") {
            Login(navController, viewModel)
        }
        composable("register_screen") {
            Register(navController, viewModel)
        }
        composable("forgot_password_screen") {
            ForgotPassword(navController, viewModel)
        }

    }



}