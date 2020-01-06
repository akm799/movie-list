package com.akm.test.movielist.data.repository

import com.akm.test.movielist.domain.model.Movie
import io.reactivex.Single

interface MovieRepository {

    fun getMovies(page: Int): Single<List<Movie>>
}