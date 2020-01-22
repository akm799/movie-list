package com.akm.test.movielist.view.ui.list.adapter

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.akm.test.movielist.R
import com.akm.test.movielist.domain.model.Movie
import com.akm.test.movielist.view.processor.MovieProcessor
import com.akm.test.movielist.view.ui.widget.PercentageView
import java.text.SimpleDateFormat
import java.util.*

class MovieViewHolder(view: View, private val movieProcessor: MovieProcessor) : RecyclerView.ViewHolder(view) {
    private companion object {
        const val PERCENTAGE_ANIMATION_MILLIS = 350L
    }

    fun bindMovie(movie: Movie) {
        bindPosterImage(movie)
        bindValues(movie)
        bindListeners(movie)
    }

    fun bindMovieFavouriteFlag(movie: Movie) {
        itemView.findViewById<CheckBox>(R.id.favourite).isChecked = movie.favourite
    }

    private fun bindPosterImage(movie: Movie) {
        Glide.with(itemView)
            .load(movie.posterImageUrl)
            .error(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(itemView.findViewById(R.id.posterImage))
    }

    private fun bindValues(movie: Movie) {
        itemView.findViewById<TextView>(R.id.movieTitle).text = movie.title
        itemView.findViewById<CheckBox>(R.id.favourite).isChecked = movie.favourite
        itemView.findViewById<TextView>(R.id.releaseDate).text = movie.releaseDate.format()
        bindVotingAverage(movie.votingAverage)
    }

    private fun bindVotingAverage(percentage: Int) {
        itemView.findViewById<TextView>(R.id.votingAverageText).text = itemView.resources.getString(R.string.percentageText, percentage)

        val percentageBar = itemView.findViewById<PercentageView>(R.id.votingAverageBar)
        setAnimatedVotingAverage(percentageBar, percentage)
    }

    private fun setAnimatedVotingAverage(percentageBar: PercentageView, percentage: Int) {
        ObjectAnimator.ofInt(percentageBar, "percentage", percentage).apply {
            duration = PERCENTAGE_ANIMATION_MILLIS
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun bindListeners(movie: Movie) {
        itemView.findViewById<CheckBox>(R.id.favourite).setOnClickListener {
            movieProcessor.toggleFavourite(movie) // No business logic here. Delegate it to the processor.
        }
    }

    private fun Date.format(): String = SimpleDateFormat("d MMMM yyyy", Locale.UK).format(this)
}