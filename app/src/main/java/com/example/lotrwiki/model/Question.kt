package com.example.lotrwiki.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    var id: Int = 0,
    var text: String = "",
    var options: List<String> = emptyList(),
    var correct: Int = 0
) : Parcelable
