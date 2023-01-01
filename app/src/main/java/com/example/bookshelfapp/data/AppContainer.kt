package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.BookshelfApi

interface AppContainer {
    val bookshelfRepository: BookshelfRepository
}

class DefaultAppContainer : AppContainer {

    override val bookshelfRepository: NetworkBookshelfRepository by lazy {
        NetworkBookshelfRepository(BookshelfApi.retrofitService)
    }
}