package com.akm.test.movielist.data.cache.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntry(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "voting_average")
    val votingAverage: Int,

    @ColumnInfo(name = "release_date")
    val releaseDate: Long,

    @ColumnInfo(name = "poster_url")
    val posterImageUrl: String,

    @ColumnInfo(name = "favourite")
    val favourite: Boolean,

    @ColumnInfo(name = "page")
    val page: Int,

    @ColumnInfo(name = "page_index")
    val pageIndex: Int
)