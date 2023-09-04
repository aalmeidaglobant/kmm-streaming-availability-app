package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingSubtitleInfoData(
    val locale: StreamingLocaleInfoData,
    val closedCaptions: Boolean,
)