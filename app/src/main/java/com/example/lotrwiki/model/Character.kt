package com.example.lotrwiki.model

import com.google.gson.annotations.SerializedName

data class Character(
    var id: String? = "",
    var name: String? = "",
    var poster: String? = "",
    var race: String? = "",
    var birth: String? = "",
    var death: String? = "",
    var weapon: String? = "",
    var titles: String? = "",
    var otherNames: String? = "",
    var family: String? = "",
    var culture: String? = "",
    var biography: String? = ""
)
