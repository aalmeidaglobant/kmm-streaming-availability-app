package com.example.moviesapp.network.dto.streaming

import kotlinx.serialization.Serializable

@Serializable
data class StreamingInfoResult(
    val service: String,
    val streamingType: String,
    val link: String,
    val videoLink: String?,
    val audios: List<StreamingAudioInfoData>,
    val subtitles: List<StreamingSubtitleInfoData>,
    val availableSince: Long,
    val leaving: Long?,
    val quality: String?,
    val price: StreamingPriceInfoData?,
    val addon: String?,
)