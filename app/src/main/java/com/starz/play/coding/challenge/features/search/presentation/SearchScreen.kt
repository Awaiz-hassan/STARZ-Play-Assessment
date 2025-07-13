package com.starz.play.coding.challenge.features.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.starz.play.coding.challenge.core.navigation.Screen
import com.starz.play.coding.challenge.core.ui.components.CenteredLoading
import com.starz.play.coding.challenge.core.ui.shared.SharedMediaViewModel
import com.starz.play.coding.challenge.core.ui.theme.STARZPLAYCodingChallengeTheme
import com.starz.play.coding.challenge.core.ui.theme.Spacing
import com.starz.play.coding.challenge.features.search.components.MediaCarousel
import com.starz.play.coding.challenge.features.search.presentation.SearchIntent.QueryChanged
import com.starz.play.coding.challenge.features.search.presentation.SearchState.Error
import com.starz.play.coding.challenge.features.search.presentation.SearchState.Idle
import com.starz.play.coding.challenge.features.search.presentation.SearchState.Loading
import com.starz.play.coding.challenge.features.search.presentation.SearchState.Success
import com.starz.play.coding.domain.model.media.MediaItem
import com.starz.play.coding.domain.model.search.MediaCategory
import com.starz.play.coding.domain.model.search.SearchResult
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    sharedViewModel: SharedMediaViewModel = hiltViewModel()
) {
    val state by viewModel.searchState.collectAsStateWithLifecycle()


    val query by viewModel.query.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize().systemBarsPadding()
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.handleIntent(QueryChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            label = { Text("Search") },
            singleLine = true
        )

        when (val currentState = state) {
            is Idle -> CenteredText("Start typing to search...")
            is Loading -> CenteredLoading()
            is Error -> CenteredText(currentState.message, isError = true)
            is Success -> {
                SearchResultList(
                    result = currentState.result,
                    onItemClick = {
                        sharedViewModel.select(it)
                        navController.navigate(Screen.Detail.route)
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchResultList(
    result: SearchResult,
    onItemClick: (MediaItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Spacing.md),
        contentPadding = PaddingValues(bottom = Spacing.lg)
    ) {
        items(items = result.categories, key = { it.mediaType }) { category ->
            MediaCarousel(
                title = category.mediaType,
                mediaItems = category.items,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun CenteredText(text: String, isError: Boolean = false) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = text,
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )
    }
}


@Preview(name = "Search Screen - Success", showSystemUi = true)
@Composable
fun PreviewSearchScreen() {
    STARZPLAYCodingChallengeTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
                OutlinedTextField(
                    value = "sample",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.md),
                    label = { Text("Search") },
                    singleLine = true
                )

                SearchResultList(
                    result = SearchResult(
                        categories = listOf(
                            MediaCategory(
                                mediaType = "Movies",
                                items = List(3) {
                                    MediaItem(
                                        id = it,
                                        title = "Movie $it",
                                        mediaType = "movie",
                                        description = "",
                                        imageUrl = "",
                                        backdropUrl = null
                                    )
                                }
                            )
                        )
                    ),
                    onItemClick = {}
                )
            }
        }
    }

}


