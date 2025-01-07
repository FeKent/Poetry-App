package com.fekent.poetryapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fekent.poetryapp.composables.AddScreen
import com.fekent.poetryapp.composables.LandingScreen
import com.fekent.poetryapp.composables.SavedScreen
import com.fekent.poetryapp.composables.SettingScreen
import com.fekent.poetryapp.data.navigationItems
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.aboretoFont

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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val appContext = LocalContext.current.applicationContext

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    val currentTitle =
                        navigationItems.find { it.route == currentRoute }?.label
                            ?: "Add Poetry"
                    Text(
                        text = currentTitle,
                        fontFamily = aboretoFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.primary,
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
        floatingActionButton = {
            if (currentRoute != "settings" && currentRoute != "add") {
                FloatingActionButton(
                    onClick = { navController.navigate("add") },
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.write_icon),
                        contentDescription = "Write",
                        contentScale = ContentScale.Fit
                    )
                }
            }
        },
        bottomBar = {
            NavigationBarView(
                currentRoute = currentRoute,
                onClick = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

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
            composable("saved") { SavedScreen() }
            composable("settings") { SettingScreen() }
            composable("add"){ AddScreen()}
        }
    }
}

@Composable
fun NavigationBarView(currentRoute: String?, onClick: (String) -> Unit) {

    NavigationBar {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    onClick(item.route)
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                },
                label = {
                    Text(
                        item.label,
                        fontFamily = aboretoFont,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
                // indicator color used for accessibility - changed for me only

            )
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PoetryPreview() {
    PoetryAppTheme {
        PoetryApp()
    }
}