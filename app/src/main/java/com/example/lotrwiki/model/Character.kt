package com.example.lotrwiki.model

data class Character(
    var id: String? = "",
    var name: String? = "",
    var poster: String? = "",
    var race: String? = null,
    var birth: String? = null,
    var death: String? = null,
    var titles: String? = "",
    var love: String? = "",
    var genre: String? = "",
    var otherNames: String? = "",
    var family: String? = null,
    var etimology: String? = "",
    var house: String? = "",
    var wikiUrl: WikiUrl? = null,
    var biography: String? = "",
    var images: List<Images> = emptyList(),
    var faction: String? = "",
    var tags: List<String> = emptyList()
)


