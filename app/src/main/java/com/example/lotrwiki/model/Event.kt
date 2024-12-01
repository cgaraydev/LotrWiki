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
    var combatants: Combatants? = null,
    var commanders: Commanders? = null,
    var strength: Strength? = null,
    var casualties: Casualties? = null,
    var wikiUrl: WikiUrl? = null,
    var images: List<Images> = emptyList(),
    var tags: List<String> = emptyList(),
    var involved: String? = null,
    var etymology: String? = null,
    var battles: String? = null
)

data class Combatants(
    var good: String? = null,
    var evil: String? = null
)

data class Commanders(
    var good: String? = null,
    var evil: String? = null
)

data class Casualties(
    var good: String? = null,
    var evil: String? = null
)

data class Strength(
    var good: String? = null,
    var evil: String? = null
)


