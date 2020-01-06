package com.akm.test.movielist.data.cache

import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow

interface MovieCache {

    fun hasMovies(): Boolean

    fun getLastMoviePage(): Int

    fun getMovies(): List<Movie>

    fun cacheMovies(page: Int, movies: List<Movie>)

    /**
     * Returns the new favourite Boolean value of the item we updated plus the item position within
     * our list. The position is returned so the item can be located in the list faster (to set the
     * updated value).
     */
    fun toggleFavourite(movieRow: MovieRow): Pair<Int, Boolean>
}