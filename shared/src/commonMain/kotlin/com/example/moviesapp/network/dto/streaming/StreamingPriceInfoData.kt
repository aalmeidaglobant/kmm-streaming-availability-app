package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingPriceInfoData(
    val amount: String,
    val currency: String,
    val formatted: String,
)

