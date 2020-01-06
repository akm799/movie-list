package com.akm.test.movielist.domain.interactor

import android.content.Context
import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.repository.MovieRepository
import com.akm.test.movielist.domain.model.Movie
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class MovieUseCaseImplTest {

    @Test
    fun shouldFetchMoviesWhenNoneCached() {
        val firstPage = 1
        val context = Mockito.mock(Context::class.java)

        val cache = Mockito.mock(MovieCache::class.java)
        Mockito.`when`(cache.hasMovies()).thenReturn(Single.just(false))

        val factory = Mockito.mock(MovieCacheFactory::class.java)
        Mockito.`when`(factory.movieCache(context)).thenReturn(cache)

        val movies = emptyList<Movie>()
        val repository = Mockito.mock(MovieRepository::class.java)
        Mockito.`when`(repository.getMovies(firstPage)).thenReturn(Single.just(movies))

        val underTest = MovieUseCaseImpl(repository, factory)
        val single = underTest.readOrFetchMovies(context)

        Assert.assertEquals(movies, single.blockingGet())
        Mockito.verify(cache).cacheMovies(firstPage, movies) // Check that we cached the fetched movies.
        Mockito.verify(cache, Mockito.never()).getMovies()
    }

    @Test
    fun shouldReadCachedMovies() {
        val movies = emptyList<Movie>()
        val context = Mockito.mock(Context::class.java)

        val cache = Mockito.mock(MovieCache::class.java)
        Mockito.`when`(cache.hasMovies()).thenReturn(Single.just(true))
        Mockito.`when`(cache.getMovies()).thenReturn(Single.just(movies))

        val factory = Mockito.mock(MovieCacheFactory::class.java)
        Mockito.`when`(factory.movieCache(context)).thenReturn(cache)

        val repository = Mockito.mock(MovieRepository::class.java)

        val underTest = MovieUseCaseImpl(repository, factory)
        val single = underTest.readOrFetchMovies(context)

        Assert.assertEquals(movies, single.blockingGet())
        Mockito.verify(repository, Mockito.never()).getMovies(Mockito.anyInt())
    }

    @Test
    fun shouldFetchMoreMovies() {
        val currentPage = 42
        val nextPage = currentPage + 1
        val context = Mockito.mock(Context::class.java)

        val cache = Mockito.mock(MovieCache::class.java)
        Mockito.`when`(cache.getLastMoviePage()).thenReturn(Single.just(currentPage))

        val factory = Mockito.mock(MovieCacheFactory::class.java)
        Mockito.`when`(factory.movieCache(context)).thenReturn(cache)

        val movies = emptyList<Movie>()
        val repository = Mockito.mock(MovieRepository::class.java)
        Mockito.`when`(repository.getMovies(nextPage)).thenReturn(Single.just(movies))

        val underTest = MovieUseCaseImpl(repository, factory)
        val single = underTest.fetchMoreMovies(context)

        Assert.assertEquals(movies, single.blockingGet())
        Mockito.verify(cache).cacheMovies(nextPage, movies) // Check that we cached the fetched movies.
    }
}