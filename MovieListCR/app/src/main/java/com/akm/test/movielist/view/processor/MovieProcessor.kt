package com.akm.test.movielist.view.processor

import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import com.akm.test.movielist.view.MovieView

/**
 * This implementation will be a central point for holding all movies and perform any 'business logic'
 * functions. Performing such functions here keeps them away from any view code containing Android
 * classes (e.g. an adapter or view holder).
 */
interface MovieProcessor {

    val movies: List<Movie>

    fun attachView(view: MovieView)

    fun onMoviesReadOrFetched(movies: List<Movie>)

    fun onMoviesReadOrFetchedError(error: Throwable)

    fun onMoreMoviesFetched(movies: List<Movie>)

    fun onMoreMoviesFetchedError(error: Throwable)

    fun toggleFavourite(movieRow: MovieRow)

    fun onFavouriteChanged(change: Pair<Int, Boolean>)

    fun onFavouriteChangedError(error: Throwable)

    fun detachView()
}