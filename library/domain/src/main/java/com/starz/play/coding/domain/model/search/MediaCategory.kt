package com.starz.play.coding.domain.model.search

import com.starz.play.coding.domain.model.media.MediaItem

data class MediaCategory(
    val mediaType: String,
    val items: List<MediaItem>
)