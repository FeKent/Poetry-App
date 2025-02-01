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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.fekent.poetryapp.composables.AddScreen
import com.fekent.poetryapp.composables.GroupedScreen
import com.fekent.poetryapp.composables.LandingScreen
import com.fekent.poetryapp.composables.SavedScreen
import com.fekent.poetryapp.composables.SettingScreen
import com.fekent.poetryapp.data.Authored
import com.fekent.poetryapp.data.AuthoredDatabase
import com.fekent.poetryapp.data.Saved
import com.fekent.poetryapp.data.SavedDatabase
import com.fekent.poetryapp.data.navigationItems
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.aboretoFont
import kotlinx.coroutines.launch

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

    val authoredDatabase = remember {
        Room.databaseBuilder(
            appContext,
            AuthoredDatabase::class.java,
            "Authored Poems"
        ).build()
    }

    val savedDatabase = remember {
        Room.databaseBuilder(
            appContext,
            SavedDatabase::class.java,
            "Saved Poems"
        ).build()
    }

//    LaunchedEffect(navController) {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            Log.d("NavBackStack", "Navigated to: ${destination.route}")
//        }
//    } used to test and fix navigation backstack

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    val currentTitle = when {
                        currentRoute?.contains("edit") == true -> "Edit Poem"
                        else -> navigationItems.find { it.route == currentRoute }?.label
                            ?: "Add Poem"
                    }
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
            if (currentRoute != "settings" && currentRoute?.contains("add") != true && currentRoute?.contains(
                    "edit"
                ) != true
            ) {
                FloatingActionButton(
                    onClick = {
                        val destination = when (currentRoute) {
                            "home" -> "add/authored"
                            "saved" -> "add/saved"
                            else -> null
                        }
                        destination?.let {
                            navController.navigate(it) {
                                // Optional flags if needed:
                                launchSingleTop =
                                    true // Prevents navigating to the same destination if already on top
                                restoreState =
                                    true    // Restores the state of the destination if it was previously saved
                            }
                        }
                    },
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
                onClick = { destination ->
                    navController.navigate(destination) {
                        // Optional flags if needed:
                        launchSingleTop =
                            true   // Prevents navigating to the same destination if it's already on top
                        restoreState =
                            true      // Restores the state of the destination if it was previously saved
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
            composable("home") {
                val deleteScope = rememberCoroutineScope()
                val poems by authoredDatabase.authoredDao().allAuthored()
                    .collectAsState(initial = emptyList())
                LandingScreen(
                    authoredPoems = poems,
                    editPoem = { poem -> navController.navigate("edit/${poem.id}") },
                    deletePoem = { poem ->
                        deleteScope.launch {
                            authoredDatabase.authoredDao().deletePoem(poem)
                        }
                        navController.popBackStack("home", inclusive = false)
                    })
            }
            composable("saved") {
                val deleteScope = rememberCoroutineScope()
                val poems by savedDatabase.savedDao().allSaved()
                    .collectAsState(initial = emptyList())
                SavedScreen(savedPoems = poems,
                    editPoem = { poem -> navController.navigate("edit/${poem.id}") },
                    deletePoem = { poem ->
                        deleteScope.launch {
                            savedDatabase.savedDao().deletePoem(poem)
                        }
                        navController.popBackStack("saved", inclusive = false)
                    })
            }
            composable("grouped") {GroupedScreen()}
            composable("settings") { SettingScreen() }
            composable("add/authored") {
                val addScreenScope = rememberCoroutineScope()
                AddScreen(isAuthored = true, poemToEdit = null, onPoemEntered = { authored, _ ->
                    addScreenScope.launch {
                        if (authored != null) {
                            authoredDatabase.authoredDao().insertPoem(authored)
                        }
                        navController.popBackStack("home", inclusive = false)
                    }
                } )
            }
            composable("add/saved") {
                val addScreenScope = rememberCoroutineScope()
                AddScreen(isAuthored = false, poemToEdit = null, onPoemEntered = { _, saved ->
                    addScreenScope.launch {
                        if (saved != null) {
                            savedDatabase.savedDao().insertPoem(saved)
                        }
                        navController.popBackStack("saved", inclusive = false)
                    }
                })
            }
            composable(
                "edit/{poemId}",
                arguments = listOf(navArgument("poemId") { type = NavType.IntType })
            ) {
                val editScreenScope = rememberCoroutineScope()
                val poemId = navBackStackEntry?.arguments?.getInt("poemId")

                if (poemId != null) {
                    val previousRoute = navController.previousBackStackEntry?.destination?.route

                    // Initialize mutable state for the poem
                    var poemToEdit: Pair<Authored?, Saved?>? by remember { mutableStateOf(null) }

                    // Fetch the appropriate poem based on the previous route
                    when (previousRoute) {
                        "home" -> {
                            LaunchedEffect(key1 = poemId) {
                                val authoredPoem = authoredDatabase.authoredDao().getPoem(poemId)
                                poemToEdit = Pair(authoredPoem, null)
                            }

                        }

                        "saved" -> {
                            LaunchedEffect(key1 = poemId) {
                                val savedPoem = savedDatabase.savedDao().getPoem(poemId)
                                poemToEdit = Pair(null, savedPoem)
                            }
                        }
                    }

                    // Pass poemToEdit to the EditScreen when it is loaded
                    poemToEdit?.let { (authoredPoem, savedPoem) ->
                        val isAuthored = authoredPoem != null

                        AddScreen(
                            poemToEdit = Pair(authoredPoem, savedPoem),
                            onPoemEntered = { editedAuthoredPoem, editedSavedPoem ->
                                editScreenScope.launch {
                                    when {
                                        editedAuthoredPoem != null -> {
                                            authoredDatabase.authoredDao()
                                                .updatePoem(editedAuthoredPoem)
                                            navController.popBackStack("home", inclusive = false)
                                        }

                                        editedSavedPoem != null -> {
                                            savedDatabase.savedDao().updatePoem(editedSavedPoem)
                                            navController.popBackStack("saved", inclusive = false)
                                        }
                                    }
                                }
                            },
                            isAuthored = isAuthored
                        )
                    }
                }
            }
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