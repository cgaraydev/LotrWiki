package com.example.lotrwiki.utils

import androidx.navigation.NavController
import com.example.lotrwiki.R
import com.example.lotrwiki.utils.customviews.CustomToolBar

fun setUpCustomToolbar(
    customToolbar: CustomToolBar,
    title: String,
    navController: NavController
) {
    customToolbar.setTitle(title)
    customToolbar.setBackButtonClickListener {
        navController.navigate(R.id.action_global_homeFragment)
    }
}