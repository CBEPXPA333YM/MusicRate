package com.example.api_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.data.deezerApi.ApiService.DeezerService
import com.example.api_test.deezerApi.DeezerViewModel
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.FavoritesViewModelFactory
import com.example.api_test.localdb.db.AppDatabase
import com.example.api_test.localdb.repo.FavoritesRepository
import com.example.api_test.nowPlaying.NowPlayingViewModel
import com.example.api_test.recommendationsApi.RecommendationsService
import com.example.api_test.recommendationsApi.RecommendationsViewModel
import com.example.api_test.recommendationsApi.RecommendationsViewModelFactory
import com.example.api_test.ui.screens.MainScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Room init
        val db = AppDatabase.getDatabase(applicationContext)
        val repository = FavoritesRepository(db.favoritesDao())
        val factory = FavoritesViewModelFactory(repository)
        val favoritesDao = db.favoritesDao()
        val recommendationsRepo = RecommendationsService()
        val deezerService = DeezerService()
        val recommendationsFactory =
            RecommendationsViewModelFactory(
                repo = recommendationsRepo,
                deezerService = deezerService,
                favoritesDao = favoritesDao
            )

        setContent {
            val deezerViewModel: DeezerViewModel = viewModel()
            val favoritesViewModel: FavoritesViewModel = viewModel(factory = factory)
            val nowPlayingViewModel: NowPlayingViewModel = viewModel()
            val recommendationsViewModel: RecommendationsViewModel = viewModel(
                factory = recommendationsFactory
            )

            MaterialTheme {
                MainScreen(
                    deezerViewModel = deezerViewModel,
                    favoritesViewModel = favoritesViewModel,
                    nowPlayingViewModel = nowPlayingViewModel,
                    recommendationsViewModel = recommendationsViewModel
                )
            }
        }
    }
}

