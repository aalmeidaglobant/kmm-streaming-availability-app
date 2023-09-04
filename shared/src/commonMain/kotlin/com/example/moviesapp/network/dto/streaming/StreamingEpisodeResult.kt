package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingEpisodeResult(
    val type: String,
    val title: String,
    val streamingInfo: StreamingInfoData,
    val year: Long,
)