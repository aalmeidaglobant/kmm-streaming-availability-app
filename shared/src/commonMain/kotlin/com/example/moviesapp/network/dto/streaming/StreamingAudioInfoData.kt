package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingAudioInfoData(
    val language: String,
    val region: String,
)