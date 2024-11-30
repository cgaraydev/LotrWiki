package com.example.lotrwiki.model

data class Other(
    var id: String = "",
    var name: String = "",
    var poster: String? = null,
    var history: String? = null,
    var category: String? = null,
    var etymology: String? = null,
    var description: String? = null,
    var otherNames: String? = null,
    var location: String? = null,
    var wikiUrl: WikiUrl? = null,
    var images: List<Images> = emptyList(),
    var tags: List<String> = emptyList(),
    var owner: String? = null,
    var creator: String? = null,
    var languages: String? = null,
    var founder: String? = null,
    var founded: String? = null,
    var leader: String? = null,
    var heirlooms: String? = null
)