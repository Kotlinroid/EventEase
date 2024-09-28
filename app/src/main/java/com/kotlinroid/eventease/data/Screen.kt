package com.kotlinroid.eventease.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

open class Screen(
    val id:String,
    val title:String,
    val icon: ImageVector
){
    object Home:Screen("home_screen","Home", Icons.Outlined.Home)
    object Ticket:Screen("welcome_screen","Tickets",Icons.Outlined.ConfirmationNumber)
    object Profile:Screen("profile","Profile",Icons.Outlined.Person)
    object Settings:Screen("settings","Settings",Icons.Outlined.Settings)

    object Items{
        val list= listOf(
            Home,Ticket,Profile,Settings
        )
    }
}
