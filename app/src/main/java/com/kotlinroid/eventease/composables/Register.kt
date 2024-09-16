package com.kotlinroid.eventease.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kotlinroid.eventease.ui.theme.ViewModel

@Composable
fun Register(navController: NavController = rememberNavController(),
             viewModel: ViewModel = remember { ViewModel() }
){
    // Register Screen Content
    Text(text = "Hello")
}