package com.akm.test.movielist.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.akm.test.movielist.domain.interactor.MovieUseCase
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.view.viewmodel.util.CallResult
import com.akm.test.movielist.view.viewmodel.util.providers.DefaultSchedulerProvider
import com.akm.test.movielist.view.viewmodel.util.providers.SchedulerProvider
import com.akm.test.movielist.view.viewmodel.util.SingleLiveDataObserver
import com.akm.test.movielist.view.viewmodel.util.providers.DefaultLiveDataProvider
import com.akm.test.movielist.view.viewmodel.util.providers.LiveDataProvider
import io.reactivex.Single

/**
 * The providers are not needed but are used for unit testing purposes. In unit tests we do not use
 * the default providers seen here, but use custom providers that return instances suitable for unit
 * testing purposes.
 */
class MovieViewModel(
    private val useCase: MovieUseCase,
    private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider(),
    private val liveDataProvider: LiveDataProvider = DefaultLiveDataProvider()
) : ViewModel() {

    fun readOrFetchMovies(): LiveData<CallResult<List<Movie>>> {
        return liveData { useCase.readOrFetchMovies() }
    }

    fun fetchMoreMovies(): LiveData<CallResult<List<Movie>>> {
        return liveData { useCase.fetchMoreMovies() }
    }

    fun toggleFavourite(movie: Movie): LiveData<CallResult<Pair<Int, Boolean>>> {
        return liveData { useCase.toggleFavourite(movie) }
    }

    private fun <T> liveData(getData: () -> Single<T>): LiveData<CallResult<T>> {
        val liveData = liveDataProvider.liveDataInstance<T>()
        val rxObserver = SingleLiveDataObserver(liveData)

        getData.invoke()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(rxObserver)

        return liveData
    }
}