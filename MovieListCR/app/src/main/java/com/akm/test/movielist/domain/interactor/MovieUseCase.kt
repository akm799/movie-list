package com.akm.test.movielist.domain.interactor

import android.content.Context
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow

interface MovieUseCase {

    suspend fun readOrFetchMovies(context: Context): List<Movie>

    suspend fun fetchMoreMovies(context: Context): List<Movie>

    /**
     * Returns the new favourite Boolean value of the item we updated plus the item position within
     * our list. The position is returned so the item can be located in the list faster (to set the
     * updated value).
     */
    suspend fun toggleFavourite(context: Context, movieRow: MovieRow): Pair<Int, Boolean>
}