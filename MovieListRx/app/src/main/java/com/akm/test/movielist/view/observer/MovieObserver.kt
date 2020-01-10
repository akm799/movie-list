package com.akm.test.movielist.view.observer

import com.akm.test.movielist.domain.model.MovieRow

interface MovieObserver {

    fun readOrFetchMovies()

    fun fetchMoreMovies()

    fun toggleFavourite(movieRow: MovieRow)
}