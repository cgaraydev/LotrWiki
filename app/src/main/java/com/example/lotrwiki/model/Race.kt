package com.example.lotrwiki.model

data class Race(
    var id: String = "",
    var name: String = "",
    var poster: String? = null,
    var history: String? = null,
    var otherNames: String? = null,
    var origins: String? = null,
    var location: String? = null,
    var languages: String? = null,
    var people: String? = null,
    var members: String? = null,
    var lifespan: String? = null,
    var height: String? = null,
    var characteristics: String? = null,
    var etymology: String? = null,
    var category: String? = null,
    var wikiUrl: WikiUrl? = null,
    var images: List<Images> = emptyList(),
    var tags: List<String> = emptyList()
)