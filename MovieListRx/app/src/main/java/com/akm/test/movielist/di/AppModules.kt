package com.akm.test.movielist.di

import com.akm.test.movielist.BuildConfig
import com.akm.test.movielist.data.api.auth.AuthInterceptor
import com.akm.test.movielist.data.api.service.MovieService
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.cache.impl.DbMovieCacheFactory
import com.akm.test.movielist.data.repository.MovieRepository
import com.akm.test.movielist.data.repository.RemoteMovieRepository
import com.akm.test.movielist.domain.interactor.MovieUseCase
import com.akm.test.movielist.domain.interactor.MovieUseCaseImpl
import com.akm.test.movielist.view.viewmodel.MovieViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



val appModule = module {
    single<Gson> { GsonBuilder().setLenient().create() }

    single { authorizedOkHttpClient(BuildConfig.API_KEY) }

    single { retrofitInstance(BuildConfig.API_BASE_URL, get(), get() )}

    single { (get() as Retrofit).create(MovieService::class.java) }

    single<MovieRepository> { RemoteMovieRepository(get()) }

    single<MovieCacheFactory> { DbMovieCacheFactory(androidApplication()) }

    single<MovieUseCase> { MovieUseCaseImpl(get(), get()) }

    viewModel { MovieViewModel(get()) }
}

private fun authorizedOkHttpClient(apiKey: String): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(apiKey))
        .build()
}

private fun retrofitInstance(baseUrl: String, gson: Gson, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
}