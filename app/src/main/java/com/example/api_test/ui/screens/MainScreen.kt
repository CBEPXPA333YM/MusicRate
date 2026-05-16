package com.example.api_test.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.api_test.deezerApi.DeezerViewModel
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.nowPlaying.NowPlayingViewModel
import com.example.api_test.recommendationsApi.RecommendationsViewModel


@Composable
fun MainScreen(
    deezerViewModel: DeezerViewModel,
    favoritesViewModel: FavoritesViewModel,
    nowPlayingViewModel: NowPlayingViewModel,
    recommendationsViewModel: RecommendationsViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),

        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            BottomNavigation {

                listOf(
                    BottomScreen.NowPlaying,
                    BottomScreen.Search,
                    BottomScreen.Favorites,
                    BottomScreen.Recommendations
                ).forEach { screen ->

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },

                        label = {
                            Text(
                                text = screen.title,
                                maxLines = 1
                            )
                        },

                        selected = currentRoute == screen.route,

                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.LightGray,

                        onClick = {

                            if (currentRoute != screen.route) {

                                navController.navigate(screen.route) {

                                    // чтобы не плодить одинаковые экраны
                                    launchSingleTop = true

                                    // восстановление состояния
                                    restoreState = true

                                    // сохранение состояния вкладок
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
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

            composable(BottomScreen.NowPlaying.route) {
                NowPlayingScreen(nowPlayingViewModel)
            }

            composable(BottomScreen.Search.route) {
                SmartSearchScreen(deezerViewModel)
            }

            composable(BottomScreen.Favorites.route) {
                FavoritesScreen(favoritesViewModel)
            }

            composable(BottomScreen.Recommendations.route) {
                RecommendationsScreen(recommendationsViewModel)
            }

        }
    }
}


