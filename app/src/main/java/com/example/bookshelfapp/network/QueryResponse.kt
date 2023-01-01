package com.example.bookshelfapp.network

import kotlinx.serialization.Serializable

@Serializable
data class QueryResponse (
    val items : List<Book>,
    val totalItems : Int,
    val kind : String,
)