package com.example.api_test.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.api_test.ui.screens.TrackDetailsScreen
import com.example.api_test.ui.theme.Api_testTheme

class TrackDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("image")

        setContent {
            Api_testTheme {
                TrackDetailsScreen(
                    title = title ?: "Unknown",
                    imageUrl = imageUrl ?: ""
                )
            }
        }
    }
}