package com.kotlinroid.eventease.composables

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kotlinroid.eventease.AuthState
import com.kotlinroid.eventease.AuthViewModel
import com.kotlinroid.eventease.R
import com.kotlinroid.eventease.ViewModel
import com.kotlinroid.eventease.ui.theme.poppinsFontFamily

@Composable
fun Login(
    navController: NavController = rememberNavController(),
    authViewModel: AuthViewModel = remember { AuthViewModel() },
    viewModel: com.kotlinroid.eventease.ViewModel,
) {
    val isInPreview = LocalInspectionMode.current
    val auth = if (!isInPreview) FirebaseAuth.getInstance() else null



    var isPasswordVisible by remember { mutableStateOf(false) }
    val padding = integerResource(id = R.integer.padding)

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> { navController.navigate("home_screen") }
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT ).show()
            else -> {}
        }

    }

    BackHandler {
        navController.navigate("welcome_screen")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
            .verticalScroll(rememberScrollState())
    )
    {

        // Back Button
        Row(modifier = Modifier
            .padding(top = padding.dp, start = padding.dp)
            .clickable { navController.navigate("welcome_screen") }
            .border(
                BorderStroke(2.dp, Color.LightGray),
                shape = RoundedCornerShape(16.dp)
            ))
        {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back Button",
                modifier = Modifier.padding(16.dp),
                tint = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }

        // Login Image
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.login_image1 else R.drawable.login_image),
            contentDescription = "Login Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.FillWidth
        )

        // Welcome Text
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, top = 16.dp, end = padding.dp),
            text = "Welcome back! Glad to see you, Again!",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            fontSize = 32.sp,
            lineHeight = 48.sp
        )

        // Email TextField
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp, top = 16.dp)
                .height(70.dp),
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                textAlign = TextAlign.Left,
                fontSize = 16.sp
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "",
                    tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            },
            label = { Text(text = "Email") },
            value = viewModel.email.value, onValueChange = { viewModel.email.value = it },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006DBD),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF006DBD),
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color(0xFF006DBD),
                focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                unfocusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))


        // Password TextFiled
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp)
                .height(70.dp),
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                textAlign = TextAlign.Left,
                fontSize = 16.sp
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "",
                    tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            },
            singleLine = true,
            label = { Text(text = "Password") },
            value = viewModel.password.value, onValueChange = { viewModel.password.value = it },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006DBD),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF006DBD),
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color(0xFF006DBD),
                focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                unfocusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password",
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Forgot Password Text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Forgot Password?",
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate("forgot_password_screen") }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                authViewModel.login(auth, viewModel.email.value, viewModel.password.value)
            },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp)
                .height(60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                contentColor = if (isSystemInDarkTheme()) Color.Black else Color.White
            )
        ) {
            Text(
                text = "Login",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

        }

        Spacer(modifier = Modifier
            .height(16.dp)
            .weight(1f))

        // Register Text
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Don't have an account? ",
                fontFamily = poppinsFontFamily,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Register Now",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF006DBD),
                modifier = Modifier.clickable { navController.navigate("register_screen") }
            )

        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val viewModel = remember { ViewModel() }
    Login(viewModel = viewModel)
}