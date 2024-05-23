package com.example.lotrwiki.utils

import com.example.lotrwiki.R

enum class Race(val fontResId: Int) {
    ELVES(R.font.half_elven_bold),
    HOBBITS(R.font.hobbiton_brush_hand),
    DRAGONS(R.font.dragonlord),
    MEN(R.font.middle_earth),
    UNKNOWN(R.font.middle_earth);

    companion object {
        fun fromString(race: String?): Race {
            return when (race) {
                "Elfos" -> ELVES
                "Hobbits" -> HOBBITS
                "Dragones" -> DRAGONS
                "Hombres" -> MEN
                else -> UNKNOWN
            }
        }
    }
}