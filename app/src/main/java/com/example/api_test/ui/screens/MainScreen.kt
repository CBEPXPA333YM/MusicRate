package com.example.api_test.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.api_test.DeezerViewModel
import com.example.api_test.ui.screens.SearchScreen.SmartSearchScreen


@Composable
fun MainScreen(viewModel: DeezerViewModel) {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
        bottomBar = {
            BottomNavigation {
                listOf(
                    BottomScreen.Search,
                    BottomScreen.Collection
                ).forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, null) },
                        label = { Text(screen.title) },
                        selected = false,
                        onClick = {
                            navController.navigate(screen.route)
                        }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = BottomScreen.Search.route,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            composable(BottomScreen.Search.route) {
                SmartSearchScreen(viewModel)
            }

            composable(BottomScreen.Collection.route) {
                CollectionScreen()
            }
        }
    }
}

