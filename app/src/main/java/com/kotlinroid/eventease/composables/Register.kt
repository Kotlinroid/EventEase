package com.kotlinroid.eventease.composables

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.rememberSaveable
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
fun Register(
    navController: NavController = rememberNavController(),
    authViewModel: AuthViewModel = remember { AuthViewModel() },
    viewModel: com.kotlinroid.eventease.ViewModel
) {
    val isInPreview = LocalInspectionMode.current
    val auth = if (!isInPreview) FirebaseAuth.getInstance() else null

    var isPasswordVisible by remember { mutableStateOf(false) }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    val padding = integerResource(id = R.integer.padding)

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate("home_screen")
            }

            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            else -> {}
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    )
    {

        // Back Button
        Row(modifier = Modifier
            .padding(top = padding.dp, start = padding.dp)
            .clickable { navController.popBackStack() }
            .border(
                BorderStroke(2.dp, Color.LightGray),
                shape = RoundedCornerShape(16.dp)
            ))
        {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back Button",
                modifier = Modifier.padding(16.dp),
                tint = Color.Black
            )
        }

        // Register Image
        Image(
            painter = painterResource(id = R.drawable.register_image),
            contentDescription = "Login Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.FillWidth
        )

        // Register Text
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, top = 16.dp, end = padding.dp),
            text = "Hello! Register to get Started",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontSize = 32.sp,
            lineHeight = 48.sp
        )


        // First Name TextField
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
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "",
                    tint = Color.Black
                )
            },
            label = { Text(text = "First Name") },
            value = firstName, onValueChange = { firstName = it },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006DBD),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF006DBD),
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color(0xFF006DBD),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Last Name TextField
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
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "",
                    tint = Color.Black
                )
            },
            label = { Text(text = "Last Name") },
            value = lastName, onValueChange = { lastName = it },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006DBD),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF006DBD),
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color(0xFF006DBD),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email TextField
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
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "",
                    tint = Color.Black
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
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phone TextField
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
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "",
                    tint = Color.Black
                )
            },
            label = { Text(text = "Phone Number") },
            value = phoneNumber, onValueChange = { phoneNumber = it },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006DBD),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF006DBD),
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color(0xFF006DBD),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
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
                    tint = Color.Black
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
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
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
                        tint = Color.Black
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Register Button
        Button(
            onClick = {
                if (viewModel.email.value.isEmpty() || viewModel.password.value.isEmpty() || phoneNumber.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(context, "Fill all the fields", Toast.LENGTH_SHORT).show()
                } else {
                    authViewModel.register(
                        auth = auth,
                        context = context,
                        email = viewModel.email.value,
                        password = viewModel.password.value,
                        phoneNumber = phoneNumber.toLong(),
                        firstName = firstName,
                        lastName = lastName
                    )
                }
            },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp)
                .height(60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Register",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

        }

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        // Login Text
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Already have an account? ",
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Login Now",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF006DBD),
                modifier = Modifier.clickable { navController.navigate("login_screen") }
            )

        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}


@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    val viewModel = remember { ViewModel() }
    Register(viewModel = viewModel)
}