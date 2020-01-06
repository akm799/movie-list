package com.akm.test.movielist.data.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
abstract class MovieDao {

    @Query("SELECT COUNT(*) FROM movies")
    abstract fun getMovieCount(): Single<Int>

    @Query("SELECT MAX(page) FROM movies")
    abstract fun getMaxMoviePage(): Single<Int>

    @Query("SELECT * FROM movies ORDER BY page, page_index")
    abstract fun getAllMovies(): Single<List<MovieEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(movies: List<MovieEntry>) // Cannot return anything from here due to a room-rxjava2 bug.

    @Query("UPDATE movies SET favourite = :favourite WHERE id = :id")
    abstract fun updateFavourite(id: Int, favourite: Boolean): Single<Int>

    @Query("SELECT favourite FROM movies WHERE id = :id")
    abstract fun getFavourite(id: Int): Single<Boolean>
}