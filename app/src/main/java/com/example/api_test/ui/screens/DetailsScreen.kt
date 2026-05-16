package com.example.api_test.ui.screens

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
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import android.content.Intent
import android.net.Uri
import java.net.URLEncoder
import androidx.compose.ui.platform.LocalContext

fun openInYandexMusic(
    context: Context,
    title: String
) {
    val query = URLEncoder.encode(title, "UTF-8")

    val url = "https://music.yandex.ru/search?text=$query"

    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )

    context.startActivity(intent)
}
@Composable
fun DetailsScreen(
    id: Long,
    title: String,
    subtitle: String,
    imageUrl: String,
    type: SmartType,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {

    val favoriteEntity by favoritesViewModel
        .getFavorite(id, type)
        .collectAsState(initial = null)

    var rating by remember { mutableStateOf(5f) }

    LaunchedEffect(favoriteEntity) {
        favoriteEntity?.rating?.let {
            rating = it.toFloat()
        }
    }

    //  Проверяем, в избранном ли
    val isFavorite by favoritesViewModel.isFavorite(id, type).collectAsState(initial = false)
    Log.d("ID", "id = $id")
    Log.d("TITLE", "title = $title")

    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(subtitle) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            (context as? Activity)?.finish()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
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
                Text(text = title, style = MaterialTheme.typography.body1)
            }
            
            item {
                Text("Ваша оценка: ${rating.toInt()}", style = MaterialTheme.typography.h6)

                Slider(
                    value = rating,
                    onValueChange = { newRating ->
                        rating = newRating

                        if (isFavorite) {
                            favoritesViewModel.updateRating(
                                id = id,
                                type = type,
                                rating = newRating.toInt()
                            )
                        }
                    },
                    valueRange = 1f..10f,
                    steps = 8 // 2..9
                )

            }

            item {
                Button(
                    onClick = {
                        val entity = FavoritesEntity(
                            id = id,
                            title = title,
                            type = type,
                            subtitle = subtitle,
                            imageUrl = imageUrl,
                            rating = rating.toInt()
                        )


                        if (isFavorite) {
                            favoritesViewModel.removeFavorite(entity)
                        } else {
                            favoritesViewModel.addFavorite(entity)
                        }
                        Log.d("FAV", "Entity = $entity")
                    }
                ) {
                    Text(
                        if (isFavorite) "Удалить из избранного"
                        else "Добавить в избранное"

                    )
                }
            }
            item {
                Button(
                    onClick = {
                        openInYandexMusic(context, title)
                    }
                ) {
                    Text("Искать в Яндекс Музыке")
                }
            }
        }
    }
}
