package com.akm.test.movielist.view.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akm.test.movielist.domain.interactor.MovieUseCase
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import com.akm.test.movielist.view.viewmodel.util.CallResult
import kotlinx.coroutines.*

class MovieViewModel(private val useCase: MovieUseCase) : ViewModel() {
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
        val liveData = MutableLiveData<CallResult<T>>()

        val job = Job()
        val coroutineScope = CoroutineScope(Dispatchers.Main + job)

        coroutineScope.launch(Dispatchers.Main) {
            runningJob = job
            liveData.value = f.invoke()
            runningJob = null
        }

        return liveData
    }

    private suspend fun <T> ioThread(f: () -> T): CallResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                CallResult(f.invoke())
            } catch (e: Exception) {
                CallResult<T>(e)
            }
        }
    }

    fun cancel() = runningJob?.cancel()
}