package com.akm.test.movielist.data.cache

import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import io.reactivex.Single

interface MovieCache {

    fun hasMovies(): Single<Boolean>

    fun getLastMoviePage(): Single<Int>

    fun getMovies(): Single<List<Movie>>

    fun cacheMovies(page: Int, movies: List<Movie>) // Cannot return anything from here due to a room-rxjava2 bug.

    /**
     * Returns the new favourite Boolean value of the item we updated plus the item position within
     * our list. The position is returned so the item can be located in the list faster (to set the
     * updated value).
     */
    fun toggleFavourite(movieRow: MovieRow): Single<Pair<Int, Boolean>>
}