package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelfapp.network.Book

@Composable
fun DetailsBookScreen(
    viewModel: BookshelfViewModel,
    modifier: Modifier = Modifier,
) {

    when(val uiState = viewModel.detailViewState) {
        is DetailViewState.Loading -> {
            LoadingScreen()
        }
        is DetailViewState.Error -> {
            ErrorScreen()
        }
        is DetailViewState.Success -> {
            BookDetailLayout(uiState.book, modifier)
        }
    }
}

@Composable
fun BookDetailLayout(
    book: Book,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        BookHeaderImage(book, modifier)
        Bookdata(book, modifier)
    }
}

@Composable
private fun BookHeaderImage(
    book: Book,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        modifier = modifier
            .heightIn(min = 160.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        model = ImageRequest.Builder(LocalContext.current)
            .data(book.volumeInfo.imageLinks?.httpsThumbnail)
            .crossfade(true)
            .build(),
        contentDescription = book.volumeInfo.title,
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun Bookdata(
    book: Book,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = book.volumeInfo.title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = book.volumeInfo.description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}