package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.Book
import com.example.bookshelfapp.network.BookshelfApiService
import com.example.bookshelfapp.network.QueryResponse

interface BookshelfRepository {
    suspend fun getBooks(query: String): List<Book>?
    suspend fun getBook(id: String): Book?
}
class NetworkBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBooks(query: String): List<Book>? {
        return try {
            val res = bookshelfApiService.getBooks(query)
            if (res.isSuccessful) {
                res.body()?.items ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBook(id: String): Book? {
        return try {
            bookshelfApiService.getBook(id).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }
}
