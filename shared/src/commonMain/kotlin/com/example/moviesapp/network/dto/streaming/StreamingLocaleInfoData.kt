package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingLocaleInfoData(
    val language: String,
    val region: String,
)