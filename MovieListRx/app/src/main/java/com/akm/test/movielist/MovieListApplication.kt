package com.akm.test.movielist

import android.app.Application
import com.akm.test.movielist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieListApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieListApplication)
            modules(appModule)
        }
    }
}