package com.example.api_test.ui.screens.SearchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.ui.SmartType

@Composable
fun DetailsScreen(
    id: Long,
    title: String,
    imageUrl: String,
    type: SmartType,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {

    val subtitle = when (type) {
        SmartType.ARTIST -> "Artist"
        SmartType.ALBUM -> "Album"
        SmartType.TRACK -> "Track"
    }

    // 👇 Проверяем, в избранном ли
    val isFavorite by favoritesViewModel.isFavorite(id, type).collectAsState(initial = false)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopAppBar(title = { Text(title) })
        }
    ) { scaffoldPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = subtitle,
                    modifier = Modifier.size(200.dp)
                )
            }

            item {
                Text(text = subtitle, style = MaterialTheme.typography.body1)
            }

            item {
                Button(
                    onClick = {
                        val entity = FavoritesEntity(
                            id = id,
                            title = title,
                            type = type,
                            subtitle = subtitle,
                            imageUrl = imageUrl
                        )

                        if (isFavorite) {
                            favoritesViewModel.removeFavorite(entity)
                        } else {
                            favoritesViewModel.addFavorite(entity)
                        }
                    }
                ) {
                    Text(
                        if (isFavorite) "Удалить из избранного"
                        else "Добавить в избранное"
                    )
                }
            }
        }
    }
}
