package com.akm.test.movielist.view.viewmodel.util.providers

import androidx.lifecycle.MutableLiveData
import com.akm.test.movielist.view.viewmodel.util.CallResult

interface LiveDataProvider {

    fun <T> liveDataInstance(): MutableLiveData<CallResult<T>>
}