package com.akm.test.movielist.view.viewmodel.util.providers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun main(): CoroutineDispatcher

    fun io(): CoroutineDispatcher
}