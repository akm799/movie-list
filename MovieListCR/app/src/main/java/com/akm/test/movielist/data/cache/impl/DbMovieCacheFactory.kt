package com.akm.test.movielist.data.cache.impl

import android.app.Application
import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.cache.db.MovieDatabase

/**
 * Force the Application as a parameter, rather than a Context, to prevent the accidental passing of
 * a non-application context as a parameter.
 */
class DbMovieCacheFactory(private val app: Application) : MovieCacheFactory {

    override fun movieCache(): MovieCache {
        val db = MovieDatabase.singleInstance(app)

        return DbMovieCache(db)
    }
}