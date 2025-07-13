package com.starz.play.coding.challenge.core.navigation


sealed class Screen(val route: String) {
    data object Search : Screen("search")
    data object Detail : Screen("detail")
    data object Player : Screen("player")
}