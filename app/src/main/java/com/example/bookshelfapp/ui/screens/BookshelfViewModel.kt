package com.example.bookshelfapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.data.BookshelfRepository
import com.example.bookshelfapp.network.Book
import kotlinx.coroutines.launch

sealed interface BookshelfUIState {
    object Loading : BookshelfUIState
    data class Success(val books: List<Book>) : BookshelfUIState
    object Error : BookshelfUIState
}

sealed interface DetailViewState {
    object Loading : DetailViewState
    data class Success(val book: Book) : DetailViewState
    object Error : DetailViewState
}


class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
): ViewModel() {
    var bookshelfUIState: BookshelfUIState by mutableStateOf(BookshelfUIState.Loading)
        private set

    var selectedBookId by mutableStateOf("")

    var detailViewState: DetailViewState by mutableStateOf(DetailViewState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks(query: String = "cicero") {
        if (query.isEmpty()) return
        viewModelScope.launch {
            bookshelfUIState = BookshelfUIState.Loading

            val books = bookshelfRepository.getBooks(query)
            bookshelfUIState = if (books.isNullOrEmpty()) {
                BookshelfUIState.Error
            } else {
                BookshelfUIState.Success(books)
            }
        }
    }

    fun getBookDetails() {
        viewModelScope.launch {
            detailViewState = DetailViewState.Loading

            val book = bookshelfRepository.getBook(selectedBookId)
            detailViewState = if (book == null) {
                DetailViewState.Error
            } else {
                DetailViewState.Success(book)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY] as BookshelfApplication?)
                val bookshelfRepository = application.appContainer.bookshelfRepository
                BookshelfViewModel(bookshelfRepository)
            }
        }
    }

}