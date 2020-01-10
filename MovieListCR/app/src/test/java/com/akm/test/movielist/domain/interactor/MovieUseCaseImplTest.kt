package com.akm.test.movielist.domain.interactor

import com.akm.test.movielist.data.cache.MovieCache
import com.akm.test.movielist.data.cache.MovieCacheFactory
import com.akm.test.movielist.data.repository.MovieRepository
import com.akm.test.movielist.domain.model.Movie
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class MovieUseCaseImplTest {

    @Test
    fun shouldFetchMoviesWhenNoneCached() {
        val firstPage = 1

        val cache = Mockito.mock(MovieCache::class.java)
        Mockito.`when`(cache.hasMovies()).thenReturn(false)

        val factory = Mockito.mock(MovieCacheFactory::class.java)
        Mockito.`when`(factory.movieCache()).thenReturn(cache)

        val expectedMovies = emptyList<Movie>()
        val repository = Mockito.mock(MovieRepository::class.java)
        Mockito.`when`(repository.getMovies(firstPage)).thenReturn(expectedMovies)

        val underTest = MovieUseCaseImpl(repository, factory)
        val actualMovies = underTest.readOrFetchMovies()

        Assert.assertEquals(expectedMovies, actualMovies)
        Mockito.verify(cache).cacheMovies(firstPage, expectedMovies) // Check that we cached the fetched movies.
        Mockito.verify(cache, Mockito.never()).getMovies()
    }

    @Test
    fun shouldReadCachedMovies() {
        val expectedMovies = emptyList<Movie>()

        val cache = Mockito.mock(MovieCache::class.java)
        Mockito.`when`(cache.hasMovies()).thenReturn(true)
        Mockito.`when`(cache.getMovies()).thenReturn(expectedMovies)

        val factory = Mockito.mock(MovieCacheFactory::class.java)
        Mockito.`when`(factory.movieCache()).thenReturn(cache)

        val repository = Mockito.mock(MovieRepository::class.java)

        val underTest = MovieUseCaseImpl(repository, factory)
        val actualMovies = underTest.readOrFetchMovies()

        Assert.assertEquals(expectedMovies, actualMovies)
        Mockito.verify(repository, Mockito.never()).getMovies(Mockito.anyInt())
    }

    @Test
    fun shouldFetchMoreMovies() {
        val currentPage = 42
        val nextPage = currentPage + 1

        val cache = Mockito.mock(MovieCache::class.java)
        Mockito.`when`(cache.getLastMoviePage()).thenReturn(currentPage)

        val factory = Mockito.mock(MovieCacheFactory::class.java)
        Mockito.`when`(factory.movieCache()).thenReturn(cache)

        val expectedMovies = emptyList<Movie>()
        val repository = Mockito.mock(MovieRepository::class.java)
        Mockito.`when`(repository.getMovies(nextPage)).thenReturn(expectedMovies)

        val underTest = MovieUseCaseImpl(repository, factory)
        val actualMovies = underTest.fetchMoreMovies()

        Assert.assertEquals(expectedMovies, actualMovies)
        Mockito.verify(cache).cacheMovies(nextPage, expectedMovies) // Check that we cached the fetched movies.
    }
}