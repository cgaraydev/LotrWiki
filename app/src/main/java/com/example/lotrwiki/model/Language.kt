package com.example.lotrwiki.model

data class Language(
    var id: String = "",
    var name: String = "",
    var history: String? = null,
    var otherNames: String? = null,
    var wikiUrl: WikiUrl? = null,
    var wordsList: List<Word> = emptyList(),
    var tags: List<String> = emptyList(),
    var images: List<Images> = emptyList()
)
