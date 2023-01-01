package com.example.bookshelfapp.ui.screens

import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelfapp.network.Book
import com.example.bookshelfapp.R

@Composable
fun HomeScreen(
    viewModel: BookshelfViewModel,
    modifier: Modifier = Modifier,
    onDetailsClick: (Book) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var query by rememberSaveable {
        mutableStateOf("")
    }

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            singleLine = true,
            placeholder = {
                Text(text = stringResource(R.string.search))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Companion.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    viewModel.getBooks(query)
                }
            ),
            modifier = Modifier
                .onKeyEvent { e ->
                    if (e.nativeKeyEvent.keyCode == KEYCODE_ENTER) {
                        focusManager.clearFocus()
                        viewModel.getBooks(query)
                    }
                    false
                }
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
        )

        when (val uiState = viewModel.bookshelfUIState) {
            is BookshelfUIState.Loading -> LoadingScreen(modifier)
            is BookshelfUIState.Success -> BookshelfGrid(uiState.books, modifier, onDetailsClick)
            is BookshelfUIState.Error -> ErrorScreen(modifier)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookshelfGrid(books: List<Book>, modifier: Modifier = Modifier, onDetailsClick: (Book) -> Unit) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            cells = GridCells.Adaptive(150.dp)
        ) {
            items(
                items = books,
            ) {

                BookCard(it, onDetailsClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookCard(book: Book, onDetailsClick: (Book) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(0.85f),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = { onDetailsClick(book) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text=book.volumeInfo.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.httpsThumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                contentScale = ContentScale.Crop,
            )
        }
    }
}