package com.example.moviesapp.network

import com.example.moviesapp.network.dto.streaming.StreamingData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class StreamingAvailabilityApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                isLenient = true
                explicitNulls = false
            })
        }
    }

    suspend fun getAllShows(): StreamingData {
        return httpClient.get("https://streaming-availability.p.rapidapi.com/search/title?title=Batman&country=us&show_type=all&output_language=en") {
            headers {
                header("X-RapidAPI-Key", "080fca6e96msh8333736ef915798p13fd22jsn8d04d60df892")
                header("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com")
            }.build()
        }.body()
    }
}