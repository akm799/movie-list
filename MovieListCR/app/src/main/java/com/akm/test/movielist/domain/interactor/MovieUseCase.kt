package com.akm.test.movielist.domain.interactor

import com.akm.test.movielist.domain.model.Movie

interface MovieUseCase {

    suspend fun readOrFetchMovies(): List<Movie>

    suspend fun fetchMoreMovies(): List<Movie>

    /**
     * Returns the new favourite Boolean value of the item we updated plus the item position within
     * our list. The movie ID is returned so the item can be located in the list (to set the updated
     * value).
     */
    suspend fun toggleFavourite(movie: Movie): Pair<Int, Boolean>
}