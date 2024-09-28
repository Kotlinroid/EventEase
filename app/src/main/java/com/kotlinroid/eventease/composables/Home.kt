package com.kotlinroid.eventease.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.provider.CalendarContract.Events
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firestore.admin.v1.Index
import com.kotlinroid.eventease.*
import com.kotlinroid.eventease.data.Categories
import com.kotlinroid.eventease.data.Movies
import com.kotlinroid.eventease.data.Popular
import com.kotlinroid.eventease.data.Screen
import com.kotlinroid.eventease.viewmodels.CardViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.sin


@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    navController: NavController = rememberNavController(),
    authViewModel: AuthViewModel = remember { AuthViewModel() },
    cardViewModel: CardViewModel = remember { CardViewModel() }

) {


    val items by cardViewModel.items.observeAsState(emptyList())
    val movies by cardViewModel.movies.observeAsState(emptyList())
    val categories by cardViewModel.categories.observeAsState(emptyList())


    val isInPreview = LocalInspectionMode.current
    val auth = if (!isInPreview) FirebaseAuth.getInstance() else null

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> {
                navController.navigate("login_screen")
            }

            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

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

    val currentScreen = mutableStateOf<Screen>(Screen.Ticket)
    Scaffold(
        bottomBar = {

                CustomBottomNavigation(currentScreenId = currentScreen.value.id) {
                    currentScreen.value = it
                }

        }
    ) { innerpadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(innerpadding)
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
                TextField(value = search.value,
                    onValueChange = { newText -> search.value = newText },
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
                    singleLine = true,
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


            LazyRow {
                items(items.size) { index ->
                    val item = items[index]
                    val size = items.size
                    EventCard(item = item, index = index, size = size)
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

            LazyRow {
                items(movies.size) { index ->
                    val item = movies[index]
                    val size = movies.size
                    MoviesCard(item = item, index = index, size = size)
                }
            }


            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(start = padding.dp, end = padding.dp)) {
                Text(
                    text = "Categories".uppercase(),
                    color = Color.Black,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

            }
            LazyRow {
                items(categories.size) { index ->
                    val item = categories[index]
                    val size = categories.size
                    CategorieCard(item = item, index = index, size = size)
                }
            }
            //Spacer(modifier = Modifier.height(20.dp))


        }

    }




}

// Popular Section Cards
@Composable
fun EventCard(item: Popular, index: Int = 1, size: Int = 1) {
    val padding = integerResource(id = R.integer.padding)

    var lastPadding = 0.dp
    if (index == size - 1)
        lastPadding = padding.dp

    Box(
        modifier = Modifier.padding(start = padding.dp, end = lastPadding),

        )
    {
        Image(
            painter = rememberAsyncImagePainter(item.image), contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .width(300.dp)
                .height(200.dp)
        )

        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
                .fillMaxWidth(),

            ) {

            Row(
                modifier = Modifier.fillMaxWidth(),

                )
            {

                Spacer(modifier = Modifier.width(210.dp))
                Column(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .size(50.dp)
                        .padding(top = 2.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    )
                {

                    Box(contentAlignment = Alignment.Center) {


                        Text(
                            text = item.month.uppercase(),
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = item.date,
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

            Text(
                text = item.type.uppercase(),
                color = Color.White,
                fontFamily = poppinsFontFamily,
            )

            Text(
                text = item.title,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,

                )

        }
    }

}

// Movies & Events Section Cards
@Composable
fun MoviesCard(item: Movies, index: Int = 1, size: Int = 1) {
    val padding = integerResource(id = R.integer.padding)
    val context = LocalContext.current

    var startPadding = 12.dp
    var lastPadding = 0.dp
    if (index == size - 1)
        lastPadding = padding.dp
    if (index == 0)
        startPadding = padding.dp

    Column(
        modifier = Modifier
            .padding(start = startPadding, end = lastPadding)
            .width(150.dp)
            .height(265.dp)
            .border(1.dp, Color(0xFFededed), RoundedCornerShape(16.dp))
            .clickable {
                Toast
                    .makeText(context, item.title, Toast.LENGTH_SHORT)
                    .show()
            }
            .background(color = Color(0xFFededed), shape = RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


        Image(
            painter = rememberAsyncImagePainter(item.image), contentDescription = "Image",
            modifier = Modifier
                .height(225.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = item.title,
            color = Color.Black,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.W600,
            fontSize = 13.sp,
            lineHeight = 15.sp,
            modifier = Modifier.padding(start = 2.dp, end = 2.dp),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

    }

}


// Popular Section Cards
@Composable
fun CategorieCard(item: Categories, index: Int = 1, size: Int = 1) {
    val padding = integerResource(id = R.integer.padding)
    val context = LocalContext.current

    var startPadding = 12.dp
    var lastPadding = 0.dp
    if (index == size - 1)
        lastPadding = padding.dp
    if (index == 0)
        startPadding = padding.dp

    Column(
        modifier = Modifier
            .padding(start = startPadding, end = lastPadding)
            .width(180.dp)
            .height(76.dp)
            .clip(shape = RoundedCornerShape(24.dp))
            .background(Color(0xFFededed))
    ) {
        Row(modifier = Modifier.padding(8.dp), ) {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(60.dp)
                    .padding(1.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
                ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = item.title,
                    color = Color.Black,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    )
                Text(text = item.number,
                    color = Color.Black,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
            }

        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomePreview() {
    Home()
}