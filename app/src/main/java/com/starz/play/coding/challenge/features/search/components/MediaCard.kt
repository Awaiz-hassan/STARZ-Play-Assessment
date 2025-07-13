package com.starz.play.coding.challenge.features.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starz.play.coding.challenge.core.ui.components.NetworkImage
import com.starz.play.coding.challenge.core.ui.theme.STARZPLAYCodingChallengeTheme
import com.starz.play.coding.challenge.core.ui.theme.Spacing
import com.starz.play.coding.domain.model.media.MediaItem

@Composable
fun MediaCard(
    mediaItem: MediaItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            imageUrl = mediaItem.imageUrl,
            contentDescription = mediaItem.title,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .aspectRatio(2f / 3f)

        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        Text(
            text = mediaItem.title,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
                .padding(horizontal = Spacing.xs)
                .height(30.dp)
                .fillMaxWidth()
        )
    }
}


@Preview(name = "Media Card", showBackground = true)
@Composable
fun PreviewMediaCard() {
    STARZPLAYCodingChallengeTheme {
        MediaCard(
            mediaItem = MediaItem(
                id = 1,
                title = "Sample Movie",
                mediaType = "movie",
                description = "Fake description",
                imageUrl = "",
                backdropUrl = null
            ),
            onClick = {}
        )
    }
}