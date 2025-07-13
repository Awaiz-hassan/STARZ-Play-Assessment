package com.starz.play.coding.challenge.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavGraph(
    navController: NavHostController,
    searchScreen: @Composable (NavBackStackEntry) -> Unit,
    detailScreen: @Composable (NavBackStackEntry) -> Unit,
    playerScreen: @Composable (NavBackStackEntry) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(Screen.Search.route) { navBackStackEntry ->
            searchScreen(navBackStackEntry)
        }
        composable(Screen.Detail.route) { navBackStackEntry ->
            detailScreen(navBackStackEntry)
        }
        composable(Screen.Player.route) { navBackStackEntry ->
            playerScreen(navBackStackEntry)
        }
    }
}
