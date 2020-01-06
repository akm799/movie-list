package com.akm.test.movielist.data.repository

import com.akm.test.movielist.data.api.model.MovieEntity
import com.akm.test.movielist.data.api.service.MovieService
import com.akm.test.movielist.domain.model.Movie
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class RemoteMovieRepository(private val service: MovieService) : MovieRepository {
    private companion object {
        const val MAX_SCORE = 10
        const val AVERAGE_TO_PERCENTAGE_CONVERSION = 100/MAX_SCORE
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"
    }

    override fun getMovies(page: Int): List<Movie> {
        val response = service.getMovies(page).execute()
        if (response.isSuccessful) {
            return response.body()?.results?.map(::toMovie) ?: emptyList()
        } else {
            throw HttpException(response)
        }
    }

    private fun toMovie(entity: MovieEntity): Movie {
        with(entity) {
            val votingAverage = Math.round(vote_average*AVERAGE_TO_PERCENTAGE_CONVERSION)
            val releaseDate = SimpleDateFormat(DATE_FORMAT, Locale.UK).parse(release_date) // Maintain compatibility with pre API26.
            val posterImageUrl = POSTER_IMAGE_BASE_URL + poster_path

            return Movie(id, title, votingAverage, releaseDate, posterImageUrl)
        }
    }
}