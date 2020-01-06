package com.akm.test.movielist.view.viewmodel

import android.content.Context
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

    fun readOrFetchMovies(context: Context): LiveData<CallResult<List<Movie>>> {
        return liveData {
            ioThread { useCase.readOrFetchMovies(context) }
        }
    }

    fun fetchMoreMovies(context: Context): LiveData<CallResult<List<Movie>>> {
        return liveData {
            ioThread { useCase.fetchMoreMovies(context) }
        }
    }

    fun toggleFavourite(context: Context, movieRow: MovieRow): LiveData<CallResult<Pair<Int, Boolean>>> {
        return liveData {
            ioThread { useCase.toggleFavourite(context, movieRow) }
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

    private suspend fun <T> ioThread(f: () -> T): CallResult<T> {
        return withContext(dispatcherProvider.io()) {
            try {
                CallResult(f.invoke())
            } catch (e: Exception) {
                CallResult<T>(e)
            }
        }
    }

    fun cancel() = runningJob?.cancel()
}