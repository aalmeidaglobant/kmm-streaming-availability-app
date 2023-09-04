package com.example.moviesapp.domain.model

sealed class Show {
    abstract val title: String
    data class Series(override val title: String) : Show()
    data class Movie(override val title: String) : Show()
}