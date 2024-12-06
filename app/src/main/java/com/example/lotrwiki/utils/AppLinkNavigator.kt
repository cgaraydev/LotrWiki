package com.example.lotrwiki.utils

import androidx.navigation.NavController
import com.example.lotrwiki.NavGraphDirections

class AppLinkNavigator(private val navController: NavController) : LinkNavigator {

    override fun navigateToDestination(destination: String, id: String) {
        when (destination) {
            "location" -> navController.navigate(NavGraphDirections.actionGlobalLocationDetailsFragment(id))
            "character" -> navController.navigate(NavGraphDirections.actionGlobalCharacterDetailsFragment(id))
            "other" -> navController.navigate(NavGraphDirections.actionGlobalOtherDetailsFragment(id))
            "language" -> navController.navigate(NavGraphDirections.actionGlobalLanguageDetailsFragment(id))
            "event" -> navController.navigate(NavGraphDirections.actionGlobalEventDetailsFragment(id))
            "race" -> navController.navigate(NavGraphDirections.actionGlobalRaceDetailsFragment(id))
        }
    }

    override fun navigateToEmpty() {
        navController.navigate(NavGraphDirections.actionGlobalEmptyFragment())
    }

}