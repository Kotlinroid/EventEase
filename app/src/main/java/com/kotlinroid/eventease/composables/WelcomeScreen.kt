package com.kotlinroid.eventease.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import com.kotlinroid.eventease.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kotlinroid.eventease.ui.theme.ViewModel
import com.kotlinroid.eventease.ui.theme.poppinsFontFamily

@Composable
fun WelcomeScreen(navController: NavController = rememberNavController(),
                  viewModel: ViewModel = remember { ViewModel() })
{

    val padding = integerResource(id = R.integer.padding)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        Spacer(modifier = Modifier.height(70.dp))

        // Top Image for the Screen
        Image(painter = painterResource(id = R.drawable.ticket_booking), contentDescription = null)

        Spacer(modifier = Modifier.weight(1f))

        // Login Button
        Button(
            onClick = {viewModel.navigateToLogin(navController)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp)
                .height(60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            )
        ) {
            Text(text = "Login", color = Color.White,
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,)

        }

        Spacer(modifier = Modifier.height(30.dp))

        // Regsiter Button
        Button(
            onClick = {viewModel.navigateToRegister(navController)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp)
                .height(60.dp)
                .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            )
        ) {
            Text(text = "Register",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                )
        }

        Spacer(modifier = Modifier.weight(0.2f))

    }

}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {

    WelcomeScreen()
}