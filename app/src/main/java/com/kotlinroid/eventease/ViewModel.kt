package com.kotlinroid.eventease

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ViewModel: ViewModel(){
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