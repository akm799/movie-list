package com.akm.test.movielist.data.repository

import com.akm.test.movielist.data.api.model.GetMoviesResponse
import com.akm.test.movielist.data.api.model.MovieEntity
import com.akm.test.movielist.data.api.service.MovieService
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RemoteMovieRepositoryTest {

    @Test
    fun shouldGetMovies() {
        val page = 1

        val voteAveragePercentage = 92
        val voteAverage = voteAveragePercentage/10f

        val releaseDate = "1977-05-25"
        val expectedReleaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.UK).parse(releaseDate)

        val entity = MovieEntity(42, "Star Wars", voteAverage, releaseDate, "/star_wars.jpg")
        val getMoviesResponse = GetMoviesResponse(page, listOf(entity))
        val call = Mockito.mock(Call::class.java) as Call<GetMoviesResponse>
        val service = Mockito.mock(MovieService::class.java)
        Mockito.`when`(call.execute()).thenReturn(Response.success(getMoviesResponse))
        Mockito.`when`(service.getMovies(page)).thenReturn(call)

        val underTest = RemoteMovieRepository(service)
        val movies = underTest.getMovies(page)

        Assert.assertEquals(1, movies.size)
        val movie = movies.first()
        Assert.assertEquals(entity.id, movie.id)
        Assert.assertEquals(entity.title, movie.title)
        Assert.assertEquals(voteAveragePercentage, movie.votingAverage)
        Assert.assertEquals(expectedReleaseDate, movie.releaseDate)
        Assert.assertEquals("https://image.tmdb.org/t/p/w200${entity.poster_path}", movie.posterImageUrl)
        Assert.assertFalse(movie.favourite)
    }
}