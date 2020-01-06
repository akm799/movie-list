package com.akm.test.movielist.view.viewmodel.util

import androidx.lifecycle.MutableLiveData
import com.akm.test.movielist.view.viewmodel.util.providers.LiveDataProvider

class TestLiveDataProvider<I>(private val liveData: MutableLiveData<CallResult<I>>) : LiveDataProvider {
    override fun <T> liveDataInstance(): MutableLiveData<CallResult<T>> {
        return liveData as MutableLiveData<CallResult<T>>
    }
}