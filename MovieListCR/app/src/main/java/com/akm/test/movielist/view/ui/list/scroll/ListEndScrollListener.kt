package com.akm.test.movielist.view.ui.list.scroll

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListEndScrollListener(private val listener: ListEndListener) : RecyclerView.OnScrollListener(), View.OnTouchListener {
    private companion object {
        const val UP = -1
        const val NONE = 0
        const val DOWN = 1

        const val NO_Y_VALUE = -1f
    }

    private var y0 = NO_Y_VALUE
    private var lastScrollDirection = NONE

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> y0 = event.y
                MotionEvent.ACTION_UP -> {
                    lastScrollDirection = determineScrollDirection(event.y)
                    y0 = NO_Y_VALUE
                }
            }
        }

        return false // We are just looking at the event.
    }

    private fun determineScrollDirection(y: Float): Int {
        val dy = y - y0

        if (dy < 0) {
            return DOWN
        } else if (dy > 0) {
            return UP
        } else {
            return NONE
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (lastScrollDirection == DOWN) {
            lastScrollDirection = NONE

            if (recyclerView.canScrollVertically(DOWN).not()) {
                listener.onListEndReached()
            }
        }
    }
}