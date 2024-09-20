package com.kotlinroid.eventease.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class ViewModel: ViewModel() {

    private val _email = mutableStateOf("")
    private val _password = mutableStateOf("")

    val email: MutableState<String> = _email
    val password: MutableState<String> = _password


    fun navigateToRegister(navController: NavController) {
        navController.navigate("register_screen")
    }

    fun navigateToLogin(navController: NavController) {
        navController.navigate("login_screen")
    }

    fun navigateToForgotPassword(navController: NavController) {
        navController.navigate("forgot_password_screen")
    }

    fun navigateToHome(navController: NavController) {
        navController.navigate("home_screen")
    }

}