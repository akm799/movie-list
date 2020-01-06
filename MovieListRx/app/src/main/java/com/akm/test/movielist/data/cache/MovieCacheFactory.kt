package com.akm.test.movielist.data.cache

import android.content.Context

interface MovieCacheFactory {

    fun movieCache(context: Context): MovieCache
}