package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamingData(
    @SerialName("result")
    val data: List<StreamingResult>,
)