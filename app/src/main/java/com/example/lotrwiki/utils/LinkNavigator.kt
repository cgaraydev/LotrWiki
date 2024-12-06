package com.example.lotrwiki.utils

interface LinkNavigator {
    fun navigateToDestination(destination: String, id: String)
    fun navigateToEmpty()
}