package com.akm.test.movielist.view

import com.akm.test.movielist.domain.model.Movie

interface MovieView {

    fun moviesSet(numberOfFavourites: Int)

    fun moviesSetError(error: Throwable)

    fun moviesAdded(numberAdded: Int)

    fun moviesAddedError(error: Throwable)

    fun toggleFavourite(movie: Movie)

    fun onFavouriteChanged(id: Int, numberOfFavourites: Int)

    fun onFavouriteChangedError(error: Throwable)
}