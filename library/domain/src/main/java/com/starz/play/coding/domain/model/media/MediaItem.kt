package com.starz.play.coding.domain.model.media

data class MediaItem(
    val id: Int,
    val mediaType: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val backdropUrl: String? = null
)