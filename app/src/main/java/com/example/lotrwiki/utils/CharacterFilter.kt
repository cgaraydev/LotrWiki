package com.example.lotrwiki.utils

import com.example.lotrwiki.model.Character

sealed class CharacterFilter {
    data object All: CharacterFilter()
    data class ByName(val name: String): CharacterFilter()
    data class ByFaction(val faction: String): CharacterFilter()
    data class ByTag(val tag: String): CharacterFilter()
    data class ByGenre(val genre: String): CharacterFilter()

    fun filter(character: Character): Boolean {
        return when(this) {
            All -> true
            is ByName -> character.name?.contains(name, ignoreCase = true) ?: false
            is ByFaction -> character.faction == faction
            is ByTag -> character.tags.contains(tag)
            is ByGenre -> character.genre == genre
        }
    }
}