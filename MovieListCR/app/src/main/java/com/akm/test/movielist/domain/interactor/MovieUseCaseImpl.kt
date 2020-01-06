package com.akm.test.movielist.domain.interactor

import android.content.Context
import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.repository.MovieRepository
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow

class MovieUseCaseImpl(
    private val repository: MovieRepository,
    private val factory: MovieCacheFactory
) : MovieUseCase {

    //TODO Move to input parameters.
    private companion object {
        const val FIRST_PAGE = 1
        const val LAST_PAGE = 500
    }

    override fun readOrFetchMovies(context: Context): List<Movie> {
        val cache = factory.movieCache(context)

        return if (cache.hasMovies()) {
            cache.getMovies()
        } else {
            fetchAndCacheMovies(cache, FIRST_PAGE)
        }
    }

    override fun fetchMoreMovies(context: Context): List<Movie> {
        val cache = factory.movieCache(context)
        val lastPage = cache.getLastMoviePage()

        return fetchAndCacheMovies(cache, lastPage + 1)
    }

    private fun fetchAndCacheMovies(cache: MovieCache, page: Int): List<Movie> {
        if (page > LAST_PAGE) {
            return emptyList()
        }

        return repository.getMovies(page).also { cache.cacheMovies(page, it) }
    }

    override fun toggleFavourite(context: Context, movieRow: MovieRow): Pair<Int, Boolean> {
        return factory.movieCache(context).toggleFavourite(movieRow)
    }
}