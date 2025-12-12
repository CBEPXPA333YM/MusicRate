package com.example.api_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import com.example.api_test.ui.screens.SmartSearchScreen

 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Создаем ViewModel внутри Compose
            val viewModel = remember { DeezerViewModel() }
            MaterialTheme {
                // Вызываем экран
                SmartSearchScreen(viewModel)
            }
        }
    }
}


