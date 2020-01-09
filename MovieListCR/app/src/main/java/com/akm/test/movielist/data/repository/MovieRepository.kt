package com.akm.test.movielist.data.repository

import com.akm.test.movielist.domain.model.Movie

interface MovieRepository {

    suspend fun getMovies(page: Int): List<Movie>
}