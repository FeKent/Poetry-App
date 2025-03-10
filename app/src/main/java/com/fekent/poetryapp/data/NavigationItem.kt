package com.fekent.poetryapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(val label: String, val route: String, val icon: ImageVector)

val navigationItems = listOf(
    NavigationItem("Home", "home", Icons.Default.Home),
    NavigationItem("Saved", "saved", Icons.Default.Star),
    NavigationItem("Grouped", "grouped", Icons.Default.Menu),
    NavigationItem("Settings", "settings", Icons.Default.Settings)
)