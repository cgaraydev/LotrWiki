package com.example.lotrwiki.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Character(
    var id: String? = "",
    var name: String? = "",
    var poster: String? = "",
    var race: String? = "desconocido",
    var birth: String? = "desconocido",
    var death: String? = "desconocido",
    var weapon: String? = "",
    var titles: String? = "",
    var otherNames: String? = "",
    var family: String? = "desconocido",
    var culture: String? = "",
    var biography: String? = "",
    var images: List<String> = emptyList()
) : Parcelable
