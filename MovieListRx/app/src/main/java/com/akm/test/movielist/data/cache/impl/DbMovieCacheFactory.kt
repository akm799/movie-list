package com.akm.test.movielist.data.cache.impl

import android.content.Context
import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.cache.db.MovieDatabase

class DbMovieCacheFactory : MovieCacheFactory {

    override fun movieCache(context: Context): MovieCache {
        val db = MovieDatabase.singleInstance(context.applicationContext)

        return DbMovieCache(db)
    }
}