package com.akm.test.movielist.data.api.model

data class MovieEntity(
    val id: Int,
    val title: String,
    val vote_average: Float,
    val release_date: String,
    val poster_path: String
)