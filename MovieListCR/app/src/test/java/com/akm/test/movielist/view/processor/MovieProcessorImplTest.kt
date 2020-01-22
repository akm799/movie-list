package com.akm.test.movielist.view.processor

import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.view.MovieView
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class MovieProcessorImplTest {

    @Test
    fun shouldSetMovies() {
        val movie = Movie(42, "Star Wars", 92, Date(), "/poster.jpg")
        val movies = listOf(movie)

        val view = Mockito.mock(MovieView::class.java)

        val underTest = MovieProcessorImpl()
        underTest.attachView(view)

        Assert.assertTrue(underTest.movies.isEmpty())

        underTest.onMoviesReadOrFetched(movies)

        Assert.assertFalse(underTest.movies.isEmpty())
        Assert.assertEquals(movies.size, underTest.movies.size)
        Assert.assertNotNull(underTest.movies.firstOrNull { it.id == movie.id })

        Mockito.verify(view).moviesSet(0)
    }

    @Test
    fun shouldAddMoreMovies() {
        val movie1 = Movie(42, "Star Wars", 92, Date(System.currentTimeMillis() - 1000L), "/poster1.jpg")
        val initMovies = listOf(movie1)

        val movie2 = Movie(99, "Star Trek", 91, Date(), "/poster2.jpg")
        val moreMovies = listOf(movie2)

        val view = Mockito.mock(MovieView::class.java)

        val underTest = MovieProcessorImpl()
        underTest.attachView(view)
        underTest.onMoviesReadOrFetched(initMovies)

        Assert.assertEquals(initMovies.size, underTest.movies.size)
        Assert.assertNotNull(underTest.movies.firstOrNull { it.id == movie1.id })

        underTest.onMoreMoviesFetched(moreMovies)

        Assert.assertEquals(initMovies.size + moreMovies.size, underTest.movies.size)
        Assert.assertNotNull(underTest.movies.firstOrNull { it.id == movie1.id })
        Assert.assertNotNull(underTest.movies.firstOrNull { it.id == movie2.id })

        Mockito.verify(view).moviesAdded(moreMovies.size)
    }

    @Test
    fun shouldChangeFavourite() {
        val movie1 = Movie(42, "Star Wars", 92, Date(System.currentTimeMillis() - 1000L), "/poster1.jpg")
        val movie2 = Movie(99, "Star Trek", 91, Date(), "/poster2.jpg")
        val movies = listOf(movie1, movie2)

        val view = Mockito.mock(MovieView::class.java)

        val underTest = MovieProcessorImpl()
        underTest.attachView(view)
        underTest.onMoviesReadOrFetched(movies)

        Assert.assertEquals(movies.size, underTest.movies.size)
        Assert.assertTrue(underTest.movies.none { it.favourite })

        val favouriteIndex = 1
        val favouriteId = movies[favouriteIndex].id
        val change = Pair(favouriteId, true)
        underTest.onFavouriteChanged(change)
        Assert.assertEquals(movies.size, underTest.movies.size)
        Assert.assertEquals(movies.size - 1, underTest.movies.count { it.favourite })
        Assert.assertEquals(change.second, underTest.movies[favouriteIndex].favourite)

        Mockito.verify(view).onFavouriteChanged(favouriteId, 1)
    }
}