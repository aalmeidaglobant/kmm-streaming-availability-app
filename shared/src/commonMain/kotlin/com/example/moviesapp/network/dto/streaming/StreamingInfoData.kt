package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamingInfoData(
    @SerialName("us")
    val data: List<StreamingInfoResult>?,
)