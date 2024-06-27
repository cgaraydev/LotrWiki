package com.example.lotrwiki.model

data class Locations(
    var id: String = "",
    var name: String = "",
    var poster: String = "",
    var capital: String = "",
    var inhabitants: String = "",
    var languages: String = "",
    var founded: String = "",
    var otherNames: String = "",
    var images: List<Images> = emptyList(),
    var type: String = ""
)
