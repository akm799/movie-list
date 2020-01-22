package com.akm.test.movielist.data.cache.impl

import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.db.MovieDatabase
import com.akm.test.movielist.data.cache.db.MovieEntry
import com.akm.test.movielist.domain.model.Movie
import io.reactivex.Single
import java.util.*

class DbMovieCache(private val db: MovieDatabase) : MovieCache {

    override fun hasMovies(): Single<Boolean> = db.movieDao().getMovieCount().map { it > 0 }

    override fun getLastMoviePage(): Single<Int> = db.movieDao().getMaxMoviePage()

    override fun getMovies(): Single<List<Movie>> {
        return db.movieDao().getAllMovies().map { it.map { it.toMovie() } }
    }

    override fun cacheMovies(page: Int, movies: List<Movie>) {
        val entries = movies.mapIndexed { index, movie -> movie.toEntry(page, index) }

        db.movieDao().insertMovies(entries)
    }

    override fun toggleFavourite(movie: Movie): Single<Pair<Int, Boolean>> {
        val changeFavourite: (Int, Boolean) -> Single<Int> = { id, value ->
            db.movieDao().updateFavourite(id, value)
        }

        val getFavourite: (Int) -> Single<Boolean> = { updateCount ->
            db.movieDao().getFavourite(movie.id)
        }

        val toResult: (Boolean) -> Single<Pair<Int, Boolean>> = { favourite ->
            Single.just(Pair(movie.id, favourite))
        }

        return changeFavourite.invoke(movie.id, movie.favourite.not()).flatMap(getFavourite).flatMap(toResult)
    }

    private fun MovieEntry.toMovie(): Movie {
        return Movie(id, title, votingAverage, Date(releaseDate), posterImageUrl, favourite)
    }

    private fun Movie.toEntry(page: Int, pageIndex: Int): MovieEntry {
        return MovieEntry(id, title, votingAverage, releaseDate.time, posterImageUrl, favourite, page, pageIndex)
    }
}