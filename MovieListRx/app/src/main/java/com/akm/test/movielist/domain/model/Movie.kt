package com.akm.test.movielist.domain.model

import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val votingAverage: Int,
    val releaseDate: Date,
    val posterImageUrl: String,
    var favourite: Boolean = false
)