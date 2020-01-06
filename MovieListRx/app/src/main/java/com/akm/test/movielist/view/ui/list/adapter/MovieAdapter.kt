package com.akm.test.movielist.view.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akm.test.movielist.R
import com.akm.test.movielist.view.processor.MovieProcessor

/**
 * This adapter contains no separate data holding element or business logic. Both are functions of
 * the processor.
 */
class MovieAdapter(private val movieProcessor: MovieProcessor) : RecyclerView.Adapter<MovieViewHolder>() {
    private companion object {
        const val ELEMENT_TYPE = 0
        const val LAST_ELEMENT_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val viewResId = if (viewType == LAST_ELEMENT_TYPE) R.layout.item_movie_last else R.layout.item_movie
        val view = LayoutInflater.from(parent.context).inflate(viewResId, parent, false)

        return MovieViewHolder(view, movieProcessor)
    }

    override fun getItemCount(): Int = movieProcessor.movies.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (isLastElement(position)) LAST_ELEMENT_TYPE else ELEMENT_TYPE
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (isLastElement(position).not()) {
            holder.bindMovie(movieProcessor.movies[position], position)
        }
    }

    private fun isLastElement(position: Int): Boolean = (position == itemCount - 1)
}