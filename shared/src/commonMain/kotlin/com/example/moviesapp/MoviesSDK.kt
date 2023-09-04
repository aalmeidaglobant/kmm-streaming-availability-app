package com.example.moviesapp

import com.example.moviesapp.network.StreamingAvailabilityApi
import com.example.moviesapp.cache.Database
import com.example.moviesapp.cache.DriverFactory
import com.example.moviesapp.domain.model.Show

class MoviesSDK(databaseDriverFactory: DriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = StreamingAvailabilityApi()

    @Throws(Exception::class)
    suspend fun getShows(forceReload: Boolean): List<Show> {
        val cachedShows = database.getAllShows()
        return if (cachedShows.isNotEmpty() && !forceReload) {
            cachedShows
        } else {
            api.getAllShows().run {
                database.clearDatabase()
                database.createEntities(this)
                database.getAllShows()
            }

        }
    }
}