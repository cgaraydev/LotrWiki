package com.example.lotrwiki.model

data class Location(
    var id: String = "",
    var name: String = "",
    var poster: String? = null,
    var capital: String? = null,
    var inhabitants: String? = null,
    var languages: String? = null,
    var founded: String? = null,
    var destroyed: String? = null,
    var otherNames: String? = null,
    var type: String? = null,
    var events: String? = null,
    var location: String? = null,
    var history: String? = null,
    var majorTowns: String? = null,
    var regions: String? = null,
    var etymology: String? = null,
    var images: List<Images> = emptyList(),
    var tags: List<String> = emptyList(),
    var wikiUrl: WikiUrl? = null
)
