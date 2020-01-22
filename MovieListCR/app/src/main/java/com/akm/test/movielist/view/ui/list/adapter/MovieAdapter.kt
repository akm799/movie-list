package com.akm.test.movielist.view.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.akm.test.movielist.R
import com.akm.test.movielist.view.processor.MovieProcessor
import com.akm.test.movielist.view.ui.list.adapter.helper.LastItemAdapter

/**
 * This adapter contains no separate data holding element or business logic. Both are functions of
 * the processor.
 */
class MovieAdapter(private val movieProcessor: MovieProcessor) : LastItemAdapter<MovieViewHolder>() {
    private companion object {
        val NO_PAYLOAD = ArrayList<Any>(0)
        const val FAVOURITE_TOGGLE_FLAG = "favourite_toggled"
    }

    override fun onCreateItemViewHolder(parent: ViewGroup): MovieViewHolder {
        return buildMovieViewHolder(parent, R.layout.item_movie)
    }

    override fun onCreateLastItemViewHolder(parent: ViewGroup): MovieViewHolder {
        return buildMovieViewHolder(parent, R.layout.item_movie_last)
    }

    private fun buildMovieViewHolder(parent: ViewGroup, viewResId: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewResId, parent, false)

        return MovieViewHolder(view, movieProcessor)
    }

    override fun getNonLastItemCount(): Int = movieProcessor.movies.size

    override fun onBindItemViewHolder(holder: MovieViewHolder, position: Int) {
        onBindItemViewHolder(holder, position, NO_PAYLOAD)
    }

    override fun onBindItemViewHolder(holder: MovieViewHolder, position: Int, payloads: MutableList<Any>) {
        val movie = movieProcessor.movies[position]

        if (onlyFavouriteFlagHasChanged(payloads)) {
            holder.bindMovieFavouriteFlag(movie)
        } else {
            holder.bindMovie(movie)
        }
    }

    private fun onlyFavouriteFlagHasChanged(payloads: MutableList<Any>): Boolean {
        return payloads.contains(FAVOURITE_TOGGLE_FLAG)
    }

    fun notifyFavouriteToggled(id: Int) {
        val position = movieProcessor.getMoviePosition(id)
        notifyItemChanged(position, FAVOURITE_TOGGLE_FLAG)
    }
}