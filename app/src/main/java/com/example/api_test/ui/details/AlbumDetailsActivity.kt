package com.example.api_test.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.api_test.ui.SmartType
import com.example.api_test.ui.screens.SearchScreen.DetailsScreen

class AlbumDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("title") ?: ""
        val image = intent.getStringExtra("image") ?: ""

        setContent {
            DetailsScreen(
                title = title,
                imageUrl = image,
                type = SmartType.ALBUM
            )
        }
    }
}