package com.akm.test.movielist.data.cache.impl

import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.db.MovieDatabase
import com.akm.test.movielist.data.cache.db.MovieEntry
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import java.util.*

class DbMovieCache(private val db: MovieDatabase) : MovieCache {

    override fun hasMovies(): Boolean = db.movieDao().getMovieCount() > 0

    override fun getLastMoviePage(): Int = db.movieDao().getMaxMoviePage()

    override fun getMovies(): List<Movie> {
        return db.movieDao().getAllMovies().map { it.toMovie() }
    }

    override fun cacheMovies(page: Int, movies: List<Movie>) {
        val entries = movies.mapIndexed { index, movie -> movie.toEntry(page, index) }

        db.movieDao().insertMovies(entries)
    }

    override fun toggleFavourite(movieRow: MovieRow): Pair<Int, Boolean> {
        val movie = movieRow.movie

        val updateCount = db.movieDao().updateFavourite(movie.id, movie.favourite.not())
        if (updateCount != 1) {
            throw Exception("DB Error: favourite not updated for movie with ID ${movie.id}")
        }

        val newFavourite = db.movieDao().getFavourite(movie.id)

        return Pair(movieRow.index, newFavourite)
    }

    private fun MovieEntry.toMovie(): Movie {
        return Movie(id, title, votingAverage, Date(releaseDate), posterImageUrl, favourite)
    }

    private fun Movie.toEntry(page: Int, pageIndex: Int): MovieEntry {
        return MovieEntry(id, title, votingAverage, releaseDate.time, posterImageUrl, favourite, page, pageIndex)
    }
}