package com.example.lotrwiki.utils

object LinkValidator {

    fun isValidLink(url: String): Boolean {
        return when {
            url.startsWith("location:") -> url.substringAfter("location:").isNotEmpty()
            url.startsWith("character:") -> url.substringAfter("character:").isNotEmpty()
            url.startsWith("other:") -> url.substringAfter("other:").isNotEmpty()
            url.startsWith("language:") -> url.substringAfter("language:").isNotEmpty()
            url.startsWith("event:") -> url.substringAfter("event:").isNotEmpty()
            url.startsWith("race:") -> url.substringAfter("race:").isNotEmpty()
            else -> false
        }
    }
}