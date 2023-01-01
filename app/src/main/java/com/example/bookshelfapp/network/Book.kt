package com.example.bookshelfapp.network

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val kind: String,
    val id: String,
    val volumeInfo: VolumeInfo,
)

@Serializable
data class VolumeInfo(
    val title: String,
    val description: String = "",
    val imageLinks: ImageLinks? = null,
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
) {
    val httpsThumbnail : String
        get() = thumbnail.replace("http", "https")
}