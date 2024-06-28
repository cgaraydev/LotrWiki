package com.example.lotrwiki.utils

import java.text.Normalizer

fun String.normalize(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace(Regex("\\p{M}"), "")
        .lowercase()
}