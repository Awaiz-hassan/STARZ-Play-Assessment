package com.starz.play.coding.challenge.features.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.starz.play.coding.challenge.core.ui.theme.STARZPLAYCodingChallengeTheme
import com.starz.play.coding.challenge.core.ui.theme.Spacing
import com.starz.play.coding.domain.model.media.MediaItem

@Composable
fun MediaCarousel(
    title: String,
    mediaItems: List<MediaItem>,
    onItemClick: (MediaItem) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.sm)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            items(items = mediaItems, key = { it.id }) { mediaItem ->
                MediaCard(
                    mediaItem = mediaItem,
                    onClick = { onItemClick(mediaItem) }
                )
            }
        }
    }
}


@Preview(name = "Media Carousel", showBackground = true)
@Composable
fun PreviewMediaCarousel() {
    MediaCarousel(
        title = "Movies",
        mediaItems = List(5) {
            MediaItem(
                id = it,
                title = "Movie $it",
                mediaType = "movie",
                description = "Description $it",
                imageUrl = "",
                backdropUrl = null
            )
        },
        onItemClick = {}
    )
}


