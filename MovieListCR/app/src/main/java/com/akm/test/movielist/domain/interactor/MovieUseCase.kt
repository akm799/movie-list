package com.akm.test.movielist.domain.interactor

import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow

interface MovieUseCase {

    suspend fun readOrFetchMovies(): List<Movie>

    suspend fun fetchMoreMovies(): List<Movie>

    /**
     * Returns the new favourite Boolean value of the item we updated plus the item position within
     * our list. The position is returned so the item can be located in the list faster (to set the
     * updated value).
     */
    suspend fun toggleFavourite(movieRow: MovieRow): Pair<Int, Boolean>
}