package com.akm.test.movielist.domain.interactor

import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.repository.MovieRepository
import com.akm.test.movielist.domain.model.Movie

class MovieUseCaseImpl(
    private val repository: MovieRepository,
    private val factory: MovieCacheFactory
) : MovieUseCase {

    //TODO Move to input parameters.
    private companion object {
        const val FIRST_PAGE = 1
        const val LAST_PAGE = 500
    }

    override suspend fun readOrFetchMovies(): List<Movie> {
        val cache = factory.movieCache()

        return if (cache.hasMovies()) {
            cache.getMovies()
        } else {
            fetchAndCacheMovies(cache, FIRST_PAGE)
        }
    }

    override suspend fun fetchMoreMovies(): List<Movie> {
        val cache = factory.movieCache()
        val lastPage = cache.getLastMoviePage()

        return fetchAndCacheMovies(cache, lastPage + 1)
    }

    private suspend fun fetchAndCacheMovies(cache: MovieCache, page: Int): List<Movie> {
        if (page > LAST_PAGE) {
            return emptyList()
        }

        return repository.getMovies(page).also { cache.cacheMovies(page, it) }
    }

    override suspend fun toggleFavourite(movie: Movie): Pair<Int, Boolean> {
        return factory.movieCache().toggleFavourite(movie)
    }
}