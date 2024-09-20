package com.kotlinroid.eventease

import androidx.compose.material3.*
import androidx.compose.material.*
import androidx.compose.ui.graphics.painter.Painter
import java.time.Month

data class Event(
    val eventImage: Painter,
    val eventType: String,
    val eventName: String,
    val eventMonth: String,
    val eventDay: String
)
