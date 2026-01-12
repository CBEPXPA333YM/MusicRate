package com.example.api_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import com.example.api_test.ui.screens.MainScreen
import com.example.api_test.ui.screens.SearchScreen.SmartSearchScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val viewModel = remember { DeezerViewModel() }

            MaterialTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}



