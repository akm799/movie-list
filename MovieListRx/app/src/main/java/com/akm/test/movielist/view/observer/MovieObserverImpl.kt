package com.akm.test.movielist.view.observer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.view.observer.specific.BaseMovieObserver
import com.akm.test.movielist.view.processor.MovieProcessor
import com.akm.test.movielist.view.viewmodel.MovieViewModel
import com.akm.test.movielist.view.viewmodel.util.CallResult

class MovieObserverImpl(
    private val owner: LifecycleOwner,
    private val viewModel: MovieViewModel,
    processor: MovieProcessor
): MovieObserver {

    private val readOrFetchMoviesObserver: Observer<CallResult<List<Movie>>> = BaseMovieObserver(
        processor, { onMoviesReadOrFetched(it) }, { onMoviesReadOrFetchedError(it) }
    )

    private val moreMoviesObserver: Observer<CallResult<List<Movie>>> = BaseMovieObserver(
        processor, { onMoreMoviesFetched(it) }, { onMoreMoviesFetchedError(it) }
    )

    private val toggleFavouriteObserver: Observer<CallResult<Pair<Int, Boolean>>> = BaseMovieObserver(
        processor, { onFavouriteChanged(it) }, { onFavouriteChangedError(it) }
    )

    override fun readOrFetchMovies() {
        viewModel.readOrFetchMovies().observe(owner, readOrFetchMoviesObserver)
    }

    override fun fetchMoreMovies() {
        viewModel.fetchMoreMovies().observe(owner, moreMoviesObserver)
    }

    override fun toggleFavourite(movie: Movie) {
        viewModel.toggleFavourite(movie).observe(owner, toggleFavouriteObserver)
    }
}