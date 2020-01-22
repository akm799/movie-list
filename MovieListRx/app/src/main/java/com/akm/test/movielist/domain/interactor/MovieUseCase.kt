package com.akm.test.movielist.domain.interactor

import com.akm.test.movielist.domain.model.Movie
import io.reactivex.Single

interface MovieUseCase {

    fun readOrFetchMovies(): Single<List<Movie>>

    fun fetchMoreMovies(): Single<List<Movie>>

    /**
     * Returns the new favourite Boolean value of the item we updated plus the item position within
     * our list. The movie ID is returned so the item can be located in the list (to set the updated
     * value).
     */
    fun toggleFavourite(movie: Movie): Single<Pair<Int, Boolean>>
}