package com.example.lotrwiki.model

data class Event(
    var id: String = "",
    var name: String = "",
    var poster: String? = null,
    var history: String? = null,
    var category: String? = null,
    var conflict: String? = null,
    var date: String? = null,
    var location: String? = null,
    var otherNames: String? = null,
    var outcome: String? = null,
    var combatants: String? = null,
    var commanders: String? = null,
    var strength: String? = null,
    var casualties: String? = null,
    var wikiUrl: WikiUrl? = null,
    var images: List<Images> = emptyList(),
    var tags: List<String> = emptyList(),
    var involved: String? = null,
    var etymology: String? = null,
    var battles: String? = null
)
