package com.kotlinroid.eventease.composables

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
fun ForgotPassword(
    navController: NavController = rememberNavController(),
    authViewModel: AuthViewModel = remember { AuthViewModel() },
    viewModel: ViewModel
) {
    val isInPreview = LocalInspectionMode.current
    val auth = if (!isInPreview) FirebaseAuth.getInstance() else null
    val context = LocalContext.current

    val padding = integerResource(id = R.integer.padding)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {

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

        // Forgot Image
        Image(
            painter = painterResource(id = R.drawable.forgot_password_image),
            contentDescription = "Login Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.FillHeight
        )

        // Forgot Text
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, top = 16.dp, end = padding.dp),
            text = "Forgot Password?",
            maxLines = 2,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontSize = 32.sp,
            lineHeight = 48.sp,

            )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp),
            text = "Provide your account's email for which you want to reset your password!",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            fontSize = 16.sp,

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

        Spacer(modifier = Modifier.height(40.dp))

        // Login Button
        Button(
            onClick = {
                if (viewModel.email.value.isEmpty()){
                    Toast.makeText(context, "Please enter your email.", Toast.LENGTH_SHORT).show()
                }else{
                authViewModel.forgotPassword(auth = auth, email = viewModel.email.value, context = context) }},

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
                text = "Verify Email",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

        }

    }
}


@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    val viewModel = remember { ViewModel() }
    ForgotPassword(viewModel = viewModel)
}