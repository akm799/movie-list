package com.akm.test.movielist.domain.interactor

import android.content.Context
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import io.reactivex.Single

interface MovieUseCase {

    fun readOrFetchMovies(context: Context): Single<List<Movie>>

    fun fetchMoreMovies(context: Context): Single<List<Movie>>

    /**
     * Returns the new favourite Boolean value of the item we updated plus the item position within
     * our list. The position is returned so the item can be located in the list faster (to set the
     * updated value).
     */
    fun toggleFavourite(context: Context, movieRow: MovieRow): Single<Pair<Int, Boolean>>
}