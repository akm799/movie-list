package com.akm.test.movielist.view.observer.specific

import androidx.lifecycle.Observer
import com.akm.test.movielist.view.processor.MovieProcessor
import com.akm.test.movielist.view.viewmodel.util.CallResult

class BaseMovieObserver<T>(
    private val processor: MovieProcessor,
    private val onSuccess: MovieProcessor.(T) -> (Unit),
    private val onError: MovieProcessor.(Throwable) -> (Unit)
) : Observer<CallResult<T>> {

    override fun onChanged(result: CallResult<T>?) {
        result?.let {
            if (it.hasResult()) {
                processor.onSuccess(it.result())
            } else {
                processor.onError(it.error())
            }
        }
    }
}