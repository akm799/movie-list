package com.akm.test.movielist.data.cache.impl

import android.app.Application
import android.content.Context
import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.cache.db.MovieDatabase

/**
 * Takes the Application, rather than just the Context, as a parameter so as to prevent passing
 * a non-application context by accident.
 */
class DbMovieCacheFactory(private val app: Application) : MovieCacheFactory {

    override fun movieCache(): MovieCache {
        val db = MovieDatabase.singleInstance(app)

        return DbMovieCache(db)
    }
}