package com.akm.test.movielist.view.ui.list.scroll

import androidx.recyclerview.widget.RecyclerView

class ListEndDetector(private val recyclerView: RecyclerView) {
    private var listEndScrollListener: ListEndScrollListener? = null

    fun setListEndListener(listener: ListEndListener) {
        listEndScrollListener = ListEndScrollListener(listener)

        listEndScrollListener?.let {
            recyclerView.addOnScrollListener(it)
            recyclerView.setOnTouchListener(it)
        }
    }

    fun removeListEndListener() {
        listEndScrollListener?.let {
            recyclerView.setOnTouchListener(null)
            recyclerView.removeOnScrollListener(it)
        }

        listEndScrollListener = null
    }
}