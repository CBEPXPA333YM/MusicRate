package com.example.api_test.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.FavoritesViewModelFactory
import com.example.api_test.localdb.db.AppDatabase
import com.example.api_test.localdb.repo.FavoritesRepository
import com.example.api_test.ui.SmartType
import com.example.api_test.ui.screens.SearchScreen.DetailsScreen

class AlbumDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val repository = FavoritesRepository(db.favoritesDao())
        val factory = FavoritesViewModelFactory(repository)

        val id = intent.getLongExtra("id", 0L)
        val title = intent.getStringExtra("title") ?: ""
        val image = intent.getStringExtra("image") ?: ""

        setContent {
            val favoritesViewModel: FavoritesViewModel =
                viewModel(factory = factory)

            DetailsScreen(
                id = id,
                title = title,
                imageUrl = image,
                type = SmartType.ALBUM,
                favoritesViewModel = favoritesViewModel
            )
        }
    }
}
