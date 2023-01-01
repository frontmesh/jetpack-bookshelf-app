package com.example.bookshelfapp.ui

import androidx.annotation.StringRes
import com.example.bookshelfapp.R

enum class BookshelfRoutes(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Details(title = R.string.book_details),
}