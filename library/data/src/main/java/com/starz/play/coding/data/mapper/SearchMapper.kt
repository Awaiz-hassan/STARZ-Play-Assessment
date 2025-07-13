package com.starz.play.coding.data.mapper

import com.starz.play.coding.data.dataSource.remote.dto.ResultDto
import com.starz.play.coding.domain.model.media.MediaItem
import com.starz.play.coding.domain.model.search.MediaCategory
import com.starz.play.coding.domain.model.search.SearchResult

private const val baseImageUrl = "https://image.tmdb.org/t/p/w500/"

fun ResultDto.toDomain(): MediaItem? {
    val type = mediaType ?: "Unknown"
    val resolvedTitle = name ?: originalName ?: title ?: originalTitle ?: "Unknown"

    return MediaItem(
        id = id ?: -1,
        mediaType = type,
        title = resolvedTitle,
        description = overview ?: "",
        imageUrl = baseImageUrl + (profilePath ?: posterPath ?: backdropPath ?: ""),
        backdropUrl = baseImageUrl + (backdropPath ?: posterPath ?: profilePath ?: "")
    )
}

fun List<MediaItem>.toSearchResult(): SearchResult {
    val movies = filter { it.mediaType.equals("movie", ignoreCase = true) }
    val tvShows = filter { it.mediaType.equals("tv", ignoreCase = true) }
    val others = filter { it.mediaType != "movie" && it.mediaType != "tv" }

    val categories = buildList {
        if (movies.isNotEmpty()) add(MediaCategory("Movies", movies))
        if (tvShows.isNotEmpty()) add(MediaCategory("TV Shows", tvShows))
        if (others.isNotEmpty()) add(MediaCategory("Others", others))
    }

    return SearchResult(categories)
}