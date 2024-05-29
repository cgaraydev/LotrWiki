package com.example.lotrwiki.model

data class Character(
    var id: String? = "",
    var name: String? = "",
    var poster: String? = "",
    var race: String? = "",
    var birth: String? = "",
    var otherNames: List<String> = emptyList()
)
