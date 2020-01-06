package com.akm.test.movielist.view.observer

import android.content.Context
import com.akm.test.movielist.domain.model.MovieRow

interface MovieObserver {

    fun readOrFetchMovies(context: Context)

    fun fetchMoreMovies(context: Context)

    fun toggleFavourite(context: Context, movieRow: MovieRow)
}