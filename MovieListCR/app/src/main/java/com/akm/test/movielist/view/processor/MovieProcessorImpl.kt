package com.akm.test.movielist.view.processor

import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import com.akm.test.movielist.view.MovieView

class MovieProcessorImpl : MovieProcessor {
    private var view: MovieView? = null
    private val movieList = ArrayList<Movie>()

    override val movies: List<Movie>
        get() = movieList

    override fun attachView(view: MovieView) {
        this.view = view
    }

    override fun onMoviesReadOrFetched(movies: List<Movie>) {
        with(movieList) {
            clear()
            addAll(movies)
        }

        val numberOfFavourites = findNumberOfFavourites()
        view?.moviesSet(numberOfFavourites)
    }

    override fun onMoviesReadOrFetchedError(error: Throwable) {
        view?.moviesSetError(error)
    }

    override fun onMoreMoviesFetched(movies: List<Movie>) {
        movieList.addAll(movies)
        view?.moviesAdded(movies.size)
    }

    override fun onMoreMoviesFetchedError(error: Throwable) {
        view?.moviesAddedError(error)
    }

    override fun toggleFavourite(movieRow: MovieRow) {
        view?.toggleFavourite(movieRow)
    }

    override fun onFavouriteChanged(change: Pair<Int, Boolean>) {
        val position = change.first
        movieList[position].favourite = change.second
        val numberOfFavourites = findNumberOfFavourites()

        view?.onFavouriteChanged(position, numberOfFavourites)
    }

    override fun onFavouriteChangedError(error: Throwable) {
        view?.onFavouriteChangedError(error)
    }

    override fun detachView() {
        view = null
    }

    // This a 'business logic' simulation method.
    private fun findNumberOfFavourites(): Int = movieList.count { it.favourite }
}