package com.akm.test.movielist.domain.interactor

import android.content.Context
import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.repository.MovieRepository
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import io.reactivex.Single

class MovieUseCaseImpl(
    private val repository: MovieRepository,
    private val factory: MovieCacheFactory
) : MovieUseCase {

    //TODO Move to input parameters.
    private companion object {
        const val FIRST_PAGE = 1
        const val LAST_PAGE = 500
    }

    override fun readOrFetchMovies(context: Context): Single<List<Movie>> {
        val cache = factory.movieCache(context)

        val movieCheck: () -> Single<Boolean> = { cache.hasMovies() }

        val getMovies: (Boolean) -> Single<List<Movie>> = { haveMovies ->
            if (haveMovies) {
                cache.getMovies()
            } else {
                fetchAndCacheMovies(cache, FIRST_PAGE)
            }
        }

        return movieCheck.invoke().flatMap(getMovies)
    }

    override fun fetchMoreMovies(context: Context): Single<List<Movie>> {
        val cache = factory.movieCache(context)

        val currentPage: () -> Single<Int> = { cache.getLastMoviePage() }

        val fetchMovies: (Int) -> Single<List<Movie>> = { lastPage ->
            fetchAndCacheMovies(cache, lastPage + 1)
        }

        return currentPage.invoke().flatMap(fetchMovies)
    }

    private fun fetchAndCacheMovies(cache: MovieCache, page: Int): Single<List<Movie>> {
        if (page > LAST_PAGE) {
            return Single.just(emptyList())
        }

        return repository.getMovies(page).map { movies ->
            cache.cacheMovies(page, movies)
            movies // Dummy identity mapping do we cache the movies just fetched.
        }
    }

    override fun toggleFavourite(context: Context, movieRow: MovieRow): Single<Pair<Int, Boolean>> {
        return factory.movieCache(context).toggleFavourite(movieRow)
    }
}