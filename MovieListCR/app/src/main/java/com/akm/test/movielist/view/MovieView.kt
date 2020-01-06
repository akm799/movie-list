package com.akm.test.movielist.view

import com.akm.test.movielist.domain.model.MovieRow

interface MovieView {

    fun moviesSet(numberOfFavourites: Int)

    fun moviesSetError(error: Throwable)

    fun moviesAdded(numberAdded: Int)

    fun moviesAddedError(error: Throwable)

    fun toggleFavourite(movieRow: MovieRow)

    fun onFavouriteChanged(position: Int, numberOfFavourites: Int)

    fun onFavouriteChangedError(error: Throwable)
}