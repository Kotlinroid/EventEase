package com.kotlinroid.eventease.composables

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlinroid.eventease.R
import com.kotlinroid.eventease.ui.theme.poppinsFontFamily
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kotlinroid.eventease.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    navController: NavController = rememberNavController(),
    authViewModel: AuthViewModel = remember { AuthViewModel() },

) {

    val events = listOf(
        Event(
            eventImage = painterResource(id = R.drawable.arijit_consert),
            eventType = "CONCERT",
            eventName = "The Arijit Singh",
            eventMonth = "NOV",
            eventDay = "23"
        ),
        Event(
            eventImage = painterResource(id = R.drawable.atif_consert),
            eventType = "CONCERT",
            eventName = "Atif Aslam",
            eventMonth = "DEC",
            eventDay = "8"
        )

    )

    val isInPreview = LocalInspectionMode.current
    val auth = if (!isInPreview) FirebaseAuth.getInstance() else null

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> { navController.navigate("login_screen") }
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT ).show()
            else -> {}
        }

    }

    // Get the current date and time using LocalDateTime
    val current = LocalDateTime.now()

    // Format the date and time
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, hh:mm a")
    val formattedDate = current.format(formatter).uppercase()


    val padding = integerResource(id = R.integer.padding)
    val search = rememberSaveable { mutableStateOf("") }


    val activity = (LocalContext.current as? Activity)
    BackHandler {
        activity?.finish()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Column {
                Text(
                    text = formattedDate,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black
                )

                Text(
                    text = "Explore Events",
                    color = Color.Black,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            }

            Box(
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .clip(CircleShape)
                    .clickable { authViewModel.logout(auth) },
                contentAlignment = Alignment.Center
            )
            {

                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.profile_image), // Replace with your image resource
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop, // Ensure the image fills the height and crops excess width
                    modifier = Modifier
                        .fillMaxHeight() // Fill the entire height of the Box
                        .fillMaxWidth()  // Fill the width of the Box (maintaining aspect ratio)
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = padding.dp, end = padding.dp)
                .clip(RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            TextField(value = search.value, onValueChange = { newText -> search.value = newText },
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFededed)),

                placeholder = { Text(text = "Search...", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                ),
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .clickable { }
                            .padding(end = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { }) { // Define what happens when the button is clicked
                            Image(
                                painter = painterResource(id = R.drawable.gradiant_background),
                                contentDescription = "Clear text",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }

                        Icon(
                            painterResource(id = R.drawable.page_info), contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(start = padding.dp, end = padding.dp))
        {
            Text(
                text = "POPULAR".uppercase(),
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Column {


            LazyRow {
                items(events.size) { index ->
                    EventCard(index, events)
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(start = padding.dp, end = padding.dp))
        {
            Text(
                text = "Movies & Events".uppercase(),
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Column {


            LazyRow {
                items(events.size) { index ->
                    EventCard(index, events)
                }
            }

        }



    }
}




@Composable
fun EventCard(index: Int, events: List<Event>)
{
    val padding = integerResource(id = R.integer.padding)
    val event = events[index]
    var lastItemPaddingEnd = 0.dp
    if (index == events.size - 1) {
        lastItemPaddingEnd = padding.dp
    }
    val image = event.eventImage

    Box(modifier = Modifier.padding(start = padding.dp, end = lastItemPaddingEnd),

        )
    {
        Image(painter = image, contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .width(300.dp)
                .height(200.dp)
        )

        Column(modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
            .fillMaxWidth(),

        ) {

            Row(modifier = Modifier.fillMaxWidth(),

                )
            {

                Spacer(modifier = Modifier.width(210.dp))
                Column(modifier = Modifier
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .size(50.dp)
                    .padding(top = 2.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    )
                {

                    Box(contentAlignment = Alignment.Center) {


                        Text(
                            text = event.eventMonth,
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = event.eventDay,
                            color = Color.Black,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(text = event.eventType,
                color = Color.White,
                fontFamily = poppinsFontFamily,
                )

            Text(text = event.eventName,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,
            )

        }
    }

}





@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomePreview() {
    Home()
}