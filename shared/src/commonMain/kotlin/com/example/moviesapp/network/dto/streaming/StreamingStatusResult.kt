package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingStatusResult(
    val statusCode: Long,
    val statusText: String,
)