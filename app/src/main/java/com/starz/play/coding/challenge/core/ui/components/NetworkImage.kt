package com.starz.play.coding.challenge.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.starz.play.coding.challenge.R

@Composable
fun NetworkImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = painterResource(R.drawable.image_place_holder),
        error = painterResource(R.drawable.image_place_holder),
        modifier = modifier
    )
}
