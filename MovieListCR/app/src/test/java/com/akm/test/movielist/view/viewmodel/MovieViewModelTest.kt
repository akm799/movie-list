package com.akm.test.movielist.view.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.akm.test.movielist.domain.interactor.MovieUseCase
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.domain.model.MovieRow
import com.akm.test.movielist.view.viewmodel.util.CallResult
import com.akm.test.movielist.view.viewmodel.util.CallResultArgumentMatcher
import com.akm.test.movielist.view.viewmodel.util.TestDispatcherProvider
import com.akm.test.movielist.view.viewmodel.util.TestLiveDataProvider
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class MovieViewModelTest {

    @Test
    fun shouldSetReadOrFetchedMovieData() {
        val context = Mockito.mock(Context::class.java)
        val movies = emptyList<Movie>()
        val useCase = Mockito.mock(MovieUseCase::class.java)
        Mockito.`when`(useCase.readOrFetchMovies(context)).thenReturn(movies)

        val mockLiveData = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<CallResult<List<Movie>>>

        val underTest = MovieViewModel(useCase,
            TestDispatcherProvider(),
            TestLiveDataProvider(mockLiveData)
        )
        Assert.assertNotNull(underTest)

        val liveData = underTest.readOrFetchMovies(context)
        Assert.assertEquals(mockLiveData, liveData)
        Mockito.verify(mockLiveData).setValue(Mockito.argThat(MovieListArgumentMatcher(movies)))
    }

    @Test
    fun shouldSetMoreMovieData() {
        val context = Mockito.mock(Context::class.java)
        val movies = emptyList<Movie>()
        val useCase = Mockito.mock(MovieUseCase::class.java)
        Mockito.`when`(useCase.fetchMoreMovies(context)).thenReturn(movies)

        val mockLiveData = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<CallResult<List<Movie>>>

        val underTest = MovieViewModel(useCase,
            TestDispatcherProvider(),
            TestLiveDataProvider(mockLiveData)
        )
        Assert.assertNotNull(underTest)

        val liveData = underTest.fetchMoreMovies(context)
        Assert.assertEquals(mockLiveData, liveData)
        Mockito.verify(mockLiveData).setValue(Mockito.argThat(MovieListArgumentMatcher(movies)))
    }

    @Test
    fun shouldSetFavouriteFlagToggleData() {
        val context = Mockito.mock(Context::class.java)
        val position = 42
        val favouriteFlag = true
        val movie = Movie(37, "Start Wars", 95, Date(), "poster.jpg")
        val movieRow = MovieRow(movie, position)
        val useCase = Mockito.mock(MovieUseCase::class.java)
        Mockito.`when`(useCase.toggleFavourite(context, movieRow)).thenReturn(Pair(position, favouriteFlag))

        val mockLiveData = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<CallResult<Pair<Int, Boolean>>>

        val underTest = MovieViewModel(useCase,
            TestDispatcherProvider(),
            TestLiveDataProvider(mockLiveData)
        )
        Assert.assertNotNull(underTest)

        val liveData = underTest.toggleFavourite(context, movieRow)
        Assert.assertEquals(mockLiveData, liveData)
        Mockito.verify(mockLiveData).setValue(Mockito.argThat(MovieToggleArgumentMatcher(position, favouriteFlag)))
    }

    private inner class MovieListArgumentMatcher(
        private val expected: List<Movie>
    ) : CallResultArgumentMatcher<List<Movie>>() {

        override fun dummyInstance(): CallResult<List<Movie>> = CallResult(emptyList())

        override fun match(result: List<Movie>): Boolean {
            return (expected == result)
        }
    }

    private inner class MovieToggleArgumentMatcher(
        private val expectedPosition: Int,
        private val expectedFavouriteFlag: Boolean
    ) : CallResultArgumentMatcher<Pair<Int, Boolean>>() {

        override fun dummyInstance(): CallResult<Pair<Int, Boolean>> = CallResult(Pair(0, false))

        override fun match(result: Pair<Int, Boolean>): Boolean {
            return (expectedPosition == result.first && expectedFavouriteFlag == result.second)
        }
    }
}