package com.akm.test.movielist.data.cache

import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow

interface MovieCache {

    suspend fun hasMovies(): Boolean

    suspend fun getLastMoviePage(): Int

    suspend fun getMovies(): List<Movie>

    suspend fun cacheMovies(page: Int, movies: List<Movie>)

    /**
     * Returns the new favourite Boolean value of the item we updated plus the item position within
     * our list. The position is returned so the item can be located in the list faster (to set the
     * updated value).
     */
    suspend fun toggleFavourite(movieRow: MovieRow): Pair<Int, Boolean>
}