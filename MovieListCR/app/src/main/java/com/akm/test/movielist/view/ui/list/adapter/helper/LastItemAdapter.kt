package com.akm.test.movielist.view.ui.list.adapter.helper

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Utility adapter that displays a list of item plus a static item at the end. This static end-element
 * is referred to as the 'last item'.
 */
abstract class LastItemAdapter<VH: RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>() {
    private companion object {
        const val ELEMENT_TYPE = 0
        const val LAST_ELEMENT_TYPE = 1
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (viewType == LAST_ELEMENT_TYPE) {
            return onCreateLastItemViewHolder(parent)
        } else {
            return onCreateItemViewHolder(parent)
        }
    }

    abstract fun onCreateItemViewHolder(parent: ViewGroup): VH

    abstract fun onCreateLastItemViewHolder(parent: ViewGroup): VH

    final override fun getItemCount(): Int = getNonLastItemCount() + 1

    abstract fun getNonLastItemCount(): Int

    final override fun getItemViewType(position: Int): Int {
        return if (isLastItem(position)) LAST_ELEMENT_TYPE else ELEMENT_TYPE
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        if (isLastItem(position).not()) {
            onBindItemViewHolder(holder, position)
        }
    }

    private fun isLastItem(position: Int): Boolean = (position == itemCount - 1)

    abstract fun onBindItemViewHolder(holder: VH, position: Int)
}