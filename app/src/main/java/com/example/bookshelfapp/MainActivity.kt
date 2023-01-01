package com.example.bookshelfapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.ui.BookshelfApp
import com.example.bookshelfapp.ui.screens.BookshelfViewModel
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookshelfAppTheme {
                val viewModel : BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
                BookshelfApp(viewModel)
            }
        }
    }
}
