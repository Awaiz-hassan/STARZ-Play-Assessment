package com.starz.play.coding.challenge.core.ui.shared

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> sharedHiltViewModel(
    navController: NavController
): VM {
    val backStackEntry = navController.previousBackStackEntry
        ?: navController.currentBackStackEntry

    return hiltViewModel(viewModelStoreOwner = backStackEntry!!)
}