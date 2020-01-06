package com.akm.test.movielist.view.observer

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
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

    override fun readOrFetchMovies(context: Context) {
        viewModel.readOrFetchMovies(context).observe(owner, readOrFetchMoviesObserver)
    }

    override fun fetchMoreMovies(context: Context) {
        viewModel.fetchMoreMovies(context).observe(owner, moreMoviesObserver)
    }

    override fun toggleFavourite(context: Context, movieRow: MovieRow) {
        viewModel.toggleFavourite(context, movieRow).observe(owner, toggleFavouriteObserver)
    }

    override fun cancel() {
        viewModel.cancel()
    }
}