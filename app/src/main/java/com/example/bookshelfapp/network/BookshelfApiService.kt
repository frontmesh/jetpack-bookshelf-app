package com.example.bookshelfapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): Response<QueryResponse>

    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): Response<Book>
}

private val BASE_URL =
    "https://www.googleapis.com/books/v1/"

private val retrofit : Retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json{
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

object BookshelfApi {
    val retrofitService : BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }
}