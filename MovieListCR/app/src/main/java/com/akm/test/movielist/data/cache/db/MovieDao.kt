package com.akm.test.movielist.data.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class MovieDao {

    @Query("SELECT COUNT(*) FROM movies")
    abstract fun getMovieCount(): Int

    @Query("SELECT MAX(page) FROM movies")
    abstract fun getMaxMoviePage(): Int

    @Query("SELECT * FROM movies ORDER BY page, page_index")
    abstract fun getAllMovies(): List<MovieEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(movies: List<MovieEntry>)

    @Query("UPDATE movies SET favourite = :favourite WHERE id = :id")
    abstract fun updateFavourite(id: Int, favourite: Boolean): Int

    @Query("SELECT favourite FROM movies WHERE id = :id")
    abstract fun getFavourite(id: Int): Boolean
}