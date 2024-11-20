package com.fekent.poetryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fekent.poetryapp.composables.LandingScreen
import com.fekent.poetryapp.data.navigationItems
import com.fekent.poetryapp.ui.theme.PoetryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PoetryAppTheme {
                PoetryApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoetryApp() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    val currentRoute = getCurrentRoute(navController = navController)
                    val currentTitle =
                        navigationItems.find { it.route == currentRoute }?.label
                            ?: "Poetry App"
                    Text(
                        text = currentTitle,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        maxLines = 1,
                    )
                },
                modifier = Modifier
                    .padding(4.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color.DarkGray,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        },
        bottomBar = {
            NavigationBarView(
                navController = navController
            )
        }) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("home") { LandingScreen() }
            composable("profile") { }
            composable("saved") { }
            composable("settings") { }
        }

    }
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun NavigationBarView(navController: NavController) {
    NavigationBar {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
