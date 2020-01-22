package com.akm.test.movielist.view.observer

import com.akm.test.movielist.domain.model.Movie

interface MovieObserver {

    fun readOrFetchMovies()

    fun fetchMoreMovies()

    fun toggleFavourite(movie: Movie)

    fun cancel()
}