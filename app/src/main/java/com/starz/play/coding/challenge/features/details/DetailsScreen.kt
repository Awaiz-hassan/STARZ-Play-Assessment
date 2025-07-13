package com.starz.play.coding.challenge.features.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.starz.play.coding.challenge.core.navigation.Screen
import com.starz.play.coding.challenge.core.ui.components.NetworkImage
import com.starz.play.coding.challenge.core.ui.shared.SharedMediaViewModel
import com.starz.play.coding.challenge.core.ui.theme.Spacing
import com.starz.play.coding.domain.model.media.MediaItem

@Composable
fun DetailScreen(
    viewModel: SharedMediaViewModel = hiltViewModel(),
    navController: NavController
) {
    val mediaItem = viewModel.selectedMedia.collectAsStateWithLifecycle().value ?: return

    Scaffold(
        topBar = {
            DetailTopBar(title = mediaItem.title) {
                navController.popBackStack()
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Details(modifier = Modifier.padding(innerPadding), mediaItem = mediaItem) {
            navController.navigate(Screen.Player.route)
        }
    }
}


@Composable
private fun Details(modifier: Modifier = Modifier, mediaItem: MediaItem, onPlayClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DetailHeaderImage(mediaItem)

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            tonalElevation = 2.dp,
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(Spacing.md)) {
                Text(
                    text = mediaItem.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                Text(
                    text = mediaItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(Spacing.lg))

                if (mediaItem.mediaType in listOf("movie", "tv")) {
                    PlayButton(onPlayClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(title: String, onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}


@Composable
private fun DetailHeaderImage(item: MediaItem) {
    NetworkImage(
        imageUrl = item.backdropUrl ?: item.imageUrl,
        contentDescription = item.title,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
    )
}


@Composable
private fun PlayButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Icon(Icons.Default.PlayArrow, contentDescription = "Play")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Play Now")
    }
}

@Composable
@Preview
fun PreviewDetails() {
    val mediaItem =MediaItem(
        -1,
        mediaType = "movie",
        title = "movies",
        description = "movies",
        imageUrl = "",
        backdropUrl = null
    )
    Scaffold(
        topBar = {
            DetailTopBar(title = mediaItem.title) {

            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Details(
            modifier = Modifier.padding(innerPadding),
            mediaItem = mediaItem
        ) {}
    }
}







