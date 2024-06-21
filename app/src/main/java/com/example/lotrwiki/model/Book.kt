package com.example.lotrwiki.model

data class Book(
    var id: String? = "",
    var name: String? = "",
    var year: String? = "",
    var publisher: String? = "",
    var editor: String? = "",
    var posthumous: Boolean = false,
    var introduction: String? = "",
    var images: List<String> = emptyList()
)
