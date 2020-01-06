package com.akm.test.movielist.data.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MovieEntry::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        private const val MOVIE_DB_NAME = "movie-db"

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun singleInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context, MOVIE_DB_NAME).also { INSTANCE = it }
            }
        }

        private fun build(context: Context, dbName: String): MovieDatabase {
            return Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, dbName).build()
        }

        fun closeInstance() {
            if (INSTANCE?.isOpen == true) {
                INSTANCE?.close()
            }

            INSTANCE = null
        }
    }

    abstract fun movieDao(): MovieDao
}