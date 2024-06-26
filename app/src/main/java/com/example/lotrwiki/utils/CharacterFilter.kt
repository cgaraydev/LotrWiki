package com.example.lotrwiki.utils

import com.example.lotrwiki.model.Character

sealed class CharacterFilter {
    data object All: CharacterFilter()
    data object Featured: CharacterFilter()
    data class ByName(val name: String): CharacterFilter()
    data class ByRace(val race: String): CharacterFilter()
    data class ByFaction(val faction: String): CharacterFilter()

    fun filter(character: Character): Boolean {
        return when(this) {
            All -> true
            Featured -> character.featured
            is ByName -> character.name?.contains(name, ignoreCase = true) ?: false
            is ByRace -> character.race == race
            is ByFaction -> character.faction == faction
        }
    }
}