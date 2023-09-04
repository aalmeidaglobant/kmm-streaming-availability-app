package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingResult(
    val type: String,
    val title: String,
    val streamingInfo: StreamingInfoData,
    val year: Long?,
    val imdbId: String,
    val tmdbId: Long,
    val originalTitle: String,
    val genres: List<StreamingGenreResult>,
    val directors: List<String>?,
    val firstAirYear: Long?,
    val lastAirYear: Long?,
    val creators: List<String>?,
    val status: StreamingStatusResult?,
    val seasonCount: Long?,
    val episodeCount: Long?,
    val seasons: List<StreamingSeasonResult>?,
)