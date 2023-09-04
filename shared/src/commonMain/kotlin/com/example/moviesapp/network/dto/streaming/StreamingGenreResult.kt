package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingGenreResult(
    val id: Long,
    val name: String,
)