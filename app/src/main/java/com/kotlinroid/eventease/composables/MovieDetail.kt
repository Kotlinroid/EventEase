package com.kotlinroid.eventease.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.kotlinroid.eventease.R
import com.kotlinroid.eventease.ui.theme.poppinsFontFamily
import com.kotlinroid.eventease.viewmodels.CardViewModel


@Composable
fun MovieDetail(
    navController: NavController = rememberNavController(),
    index: Int?,
    cardViewModel: CardViewModel = remember { CardViewModel() }
) {
    val movies by cardViewModel.movies.observeAsState(emptyList())
    val items = movies[index!!]
    val padding = integerResource(id = R.integer.padding)


    Column(modifier = Modifier.fillMaxSize())
    {
        Box() {
            Image(
                painter = rememberAsyncImagePainter(model = items.image),
                contentDescription = items.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )

            IconButton(
                onClick = {navController.popBackStack()},
                modifier = Modifier
                    .size(110.dp)
                    .padding(24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(
                        BorderStroke(2.dp, Color.LightGray),
                        shape = RoundedCornerShape(16.dp)
                    ).clickable { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Box() {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(400.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.7f),
                                        Color.White
                                    )
                                )
                            )
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {


                    }
                }

                Column(modifier = Modifier
                    .padding(start = padding.dp, end = padding.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )) {
                    Spacer(modifier = Modifier.height(475.dp))
                    Text(
                        text = items.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily
                    )
                    Text(
                        text = items.duration + " \u2022 " + items.genre + " \u2022 " + items.certificate + " \u2022 " + items.date + " " + items.month.take(
                            3
                        ) + ", " + items.year,
                        color = Color.Black,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Box(
                        modifier = Modifier
                            .background(
                                Color.Black,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = "ABOUT", color = Color.White,
                            fontWeight = FontWeight(600)
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = items.description,
                        color = Color.Black,
                        modifier = Modifier
                            .height(160.dp)
                            .verticalScroll(rememberScrollState()),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.W600,
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Column {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(54.dp),
                            contentAlignment = Alignment.Center){

                                Image(painter = painterResource(id = R.drawable.gradiant_background),
                                    contentDescription = "Buy ticket",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                    )
                                Text(text = "Buy tickets for \u20B9${items.price}",
                                    color = Color.White,
                                    fontFamily = poppinsFontFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                    )


                        }

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }

        }

    }

}
