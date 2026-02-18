package com.example.api_test.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_test.details.AlbumDetailsActivity
import com.example.api_test.localdb.FavoritesViewModel
import com.example.api_test.localdb.entity.FavoritesEntity
import com.example.api_test.ui.SmartCard
import com.example.api_test.ui.SmartItem

@Composable
fun CollectionScreen(
    favoritesViewModel: FavoritesViewModel = viewModel()
) {

    val favorites by favoritesViewModel.getAllFavorites()
        .collectAsState(initial = emptyList())

    val context = LocalContext.current

    LazyColumn {
        items(favorites) { entity ->
            val item = entity.toSmartItem()

            SmartCard(item = item) {
                val intent = Intent(context, AlbumDetailsActivity::class.java).apply {
                    putExtra("id", item.id)
                    putExtra("title", item.title)
                    putExtra("image", item.imageUrl)
                    putExtra("type", item.type.name)
                }
                context.startActivity(intent)
            }
        }
    }
}
@Composable
fun FavoriteItem(item: FavoritesEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.h6)
            Text(text = item.type.name, style = MaterialTheme.typography.body2)
        }
    }
}

fun FavoritesEntity.toSmartItem(): SmartItem {
    return SmartItem(
        id = id,
        title = title,
        imageUrl = imageUrl,
        subtitle = subtitle,
        type = type
    )
}