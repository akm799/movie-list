package com.akm.test.movielist.view.viewmodel.util

import androidx.lifecycle.MutableLiveData
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class SingleLiveDataObserver<T>(private val liveData: MutableLiveData<CallResult<T>>) : SingleObserver<T>, DisposableObserver {
    private val compositeDisposable = CompositeDisposable()

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onSuccess(t: T) {
        liveData.value = CallResult(t)
    }

    override fun onError(e: Throwable) {
        liveData.value = CallResult(e)
    }

    override fun dispose() = compositeDisposable.clear()
}