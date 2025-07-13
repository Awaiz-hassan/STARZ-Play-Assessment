package com.starz.play.coding.challenge.core.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.starz.play.coding.challenge.core.navigation.AppNavGraph
import com.starz.play.coding.challenge.core.ui.shared.sharedHiltViewModel
import com.starz.play.coding.challenge.core.ui.theme.STARZPLAYCodingChallengeTheme
import com.starz.play.coding.challenge.features.details.DetailScreen
import com.starz.play.coding.challenge.features.player.PlayerScreen
import com.starz.play.coding.challenge.features.search.presentation.SearchScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            STARZPLAYCodingChallengeTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(
                        navController = navController,
                        searchScreen = { backStackEntry ->
                            SearchScreen(
                                navController = navController,
                                sharedViewModel = hiltViewModel(backStackEntry)
                            )
                        },
                        detailScreen = {
                            DetailScreen(
                                navController = navController,
                                viewModel = sharedHiltViewModel(navController)
                            )
                        },
                        playerScreen = {
                            PlayerScreen(
                                navController = navController,
                                viewModel = sharedHiltViewModel(navController)
                            )
                        }
                    )
                }
            }
        }
    }
}
