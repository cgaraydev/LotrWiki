package com.example.lotrwiki.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Race(
    var image: String? = "",
    var name: String? = "",
    var id: String? = "",
    var description: String? = "",

)