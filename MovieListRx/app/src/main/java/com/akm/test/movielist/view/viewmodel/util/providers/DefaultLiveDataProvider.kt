package com.akm.test.movielist.view.viewmodel.util.providers

import androidx.lifecycle.MutableLiveData
import com.akm.test.movielist.view.viewmodel.util.CallResult

class DefaultLiveDataProvider : LiveDataProvider {

    override fun <T> liveDataInstance(): MutableLiveData<CallResult<T>> = MutableLiveData()
}