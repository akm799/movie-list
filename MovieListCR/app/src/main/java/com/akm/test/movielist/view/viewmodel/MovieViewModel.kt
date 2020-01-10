package com.akm.test.movielist.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.akm.test.movielist.domain.interactor.MovieUseCase
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import com.akm.test.movielist.view.viewmodel.util.CallResult
import com.akm.test.movielist.view.viewmodel.util.providers.DefaultDispatcherProvider
import com.akm.test.movielist.view.viewmodel.util.providers.DefaultLiveDataProvider
import com.akm.test.movielist.view.viewmodel.util.providers.DispatcherProvider
import com.akm.test.movielist.view.viewmodel.util.providers.LiveDataProvider
import kotlinx.coroutines.*

/**
 * The providers are not needed but are used for unit testing purposes. In unit tests we do not use
 * the default providers seen here, but use custom providers that return instances suitable for unit
 * testing purposes.
 */
class MovieViewModel(
    private val useCase: MovieUseCase,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider(),
    private val liveDataProvider: LiveDataProvider = DefaultLiveDataProvider()
) : ViewModel() {
    private var runningJob: Job? = null

    fun readOrFetchMovies(): LiveData<CallResult<List<Movie>>> {
        return liveData {
            ioThread { useCase.readOrFetchMovies() }
        }
    }

    fun fetchMoreMovies(): LiveData<CallResult<List<Movie>>> {
        return liveData {
            ioThread { useCase.fetchMoreMovies() }
        }
    }

    fun toggleFavourite(movieRow: MovieRow): LiveData<CallResult<Pair<Int, Boolean>>> {
        return liveData {
            ioThread { useCase.toggleFavourite(movieRow) }
        }
    }

    private fun <T> liveData(f: suspend () -> CallResult<T>): LiveData<CallResult<T>> {
        val liveData = liveDataProvider.liveDataInstance<T>()

        val job = Job()
        val coroutineScope = CoroutineScope(dispatcherProvider.main() + job)

        coroutineScope.launch(dispatcherProvider.main()) {
            runningJob = job
            liveData.value = f.invoke()
            runningJob = null
        }

        return liveData
    }

    private suspend fun <T> ioThread(f: suspend () -> T): CallResult<T> {
        return withContext(dispatcherProvider.io()) {
            try {
                CallResult(f.invoke())
            } catch (t: Throwable) {
                CallResult<T>(t)
            }
        }
    }

    fun cancel() = runningJob?.cancel()
}