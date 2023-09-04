package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingSeasonResult(
    val type: String,
    val title: String,
    val streamingInfo: StreamingInfoData,
    val firstAirYear: Long,
    val lastAirYear: Long,
    val episodes: List<StreamingEpisodeResult>,
)