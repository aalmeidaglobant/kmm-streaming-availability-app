package com.example.moviesapp.cache

import app.cash.sqldelight.ColumnAdapter
import com.example.moviesapp.domain.model.Show
import com.example.moviesapp.network.dto.streaming.StreamingData
import com.example.moviesapp.network.dto.streaming.StreamingGenreResult
import com.example.moviesapp.network.dto.streaming.StreamingResult
import kotlinx.serialization.json.Json

internal class Database(databaseDriverFactory: DriverFactory) {
    private val database = AppDatabase(
        databaseDriverFactory.createDriver(),
        createMovieAdapter(),
        createSeriesAdapter()
    )
    private val dbQuery = database.appDatabaseQueries

    private fun createMovieAdapter(): MovieEntity.Adapter {
        val adapter = object : ColumnAdapter<List<String>, String> {
            override fun decode(databaseValue: String): List<String> =
                if (databaseValue.isEmpty()) {
                    emptyList()
                } else {
                    databaseValue.split(",")
                }

            override fun encode(value: List<String>) =
                value.joinToString(separator = ",")

        }

        return MovieEntity.Adapter(adapter, adapter, adapter)
    }

    private fun createSeriesAdapter(): SeriesEntity.Adapter {
        val adapter = object : ColumnAdapter<List<String>, String> {
            override fun decode(databaseValue: String): List<String> =
                if (databaseValue.isEmpty()) {
                    emptyList()
                } else {
                    databaseValue.split(",")
                }

            override fun encode(value: List<String>) =
                value.joinToString(separator = ",")

        }

        return SeriesEntity.Adapter(adapter, adapter, adapter, adapter)
    }

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllMovies()
            dbQuery.removeAllSeries()
        }
    }

    internal fun getAllShows(): List<Show> {
        val movies = dbQuery.selectAllMovies(::mapMovieSelecting).executeAsList()
        val series = dbQuery.selectAllSeries(::mapSeriesSelecting).executeAsList()
        return movies.plus(series).distinct().shuffled()
    }

    private fun mapMovieSelecting(
        tmdbId: Long,
        title: String,
        directors: List<String>,
        services: List<String>,
        imdbId: String,
        originalTitle: String?,
        genres: List<String>,
    ): Show.Movie = Show.Movie(title)

    private fun mapSeriesSelecting(
        tmdbId: Long,
        title: String,
        creators: List<String>,
        services: List<String>,
        imdbId: String,
        originalTitle: String?,
        genres: List<String>,
        status: List<String>,
        seasonCount: Long,
        episodeCount: Long,
    ): Show.Series = Show.Series(title)

    internal fun createEntities(streamingData: StreamingData) {
        dbQuery.transaction {
            val filteredData = streamingData.data.distinctBy { it.title }
            filteredData.forEach { streamingResult ->
                if (streamingResult.type == "movie") {
                    insertMovie(streamingResult)
                } else {
                    insertSeries(streamingResult)
                }
            }
        }
    }

    private fun insertMovie(streamingResult: StreamingResult) {
        dbQuery.insertMovie(
            streamingResult.tmdbId,
            streamingResult.originalTitle,
            streamingResult.directors.orEmpty(),
            streamingResult.streamingInfo.data?.map { it.service }.orEmpty(),
            streamingResult.imdbId,
            streamingResult.originalTitle,
            streamingResult.genres.map { it.name },
        )
    }

    private fun insertSeries(streamingResult: StreamingResult) {
        dbQuery.insertSeries(
            streamingResult.tmdbId,
            streamingResult.originalTitle,
            streamingResult.directors.orEmpty(),
            streamingResult.streamingInfo.data?.map { it.service }.orEmpty(),
            streamingResult.imdbId,
            streamingResult.originalTitle,
            streamingResult.genres.map { it.name },
            streamingResult.creators.orEmpty(),
            streamingResult.seasonCount ?: 0,
            streamingResult.episodeCount ?: 0
        )
    }
}
