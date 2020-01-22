package com.akm.test.movielist.view.processor

import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.view.MovieView

class MovieProcessorImpl : MovieProcessor {
    private var view: MovieView? = null
    private val movieList = ArrayList<Movie>()

    override val movies: List<Movie>
        get() = movieList

    override fun getMoviePosition(id: Int): Int {
        movieList.forEachIndexed { index, movie ->
            if (movie.id == id) {
                return index
            }
        }

        throw IllegalArgumentException("Unknown ID parameter: $id")
    }

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

    override fun toggleFavourite(movie: Movie) {
        view?.toggleFavourite(movie)
    }

    override fun onFavouriteChanged(change: Pair<Int, Boolean>) {
        val id = change.first
        findMovieById(id).favourite = change.second
        val numberOfFavourites = findNumberOfFavourites()

        view?.onFavouriteChanged(id, numberOfFavourites)
    }

    private fun findMovieById(id: Int): Movie {
        return movieList.firstOrNull { it.id == id } ?: throw IllegalArgumentException("Unknown ID parameter: $id")
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