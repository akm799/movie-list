package com.akm.test.movielist.view.ui.widget.list

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * A RecyclerView that can only accept a LinearLayoutManager as its layout manager and that saves
 * and restores its scroll position in configuration changes.
 */
class LinearRecyclerView : RecyclerView {
    private companion object {
        const val UP = -1
    }

    private var positionToScroll = NO_POSITION

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        isSaveEnabled = true
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is LinearLayoutManager) {
            super.setLayoutManager(layout)
        } else {
            throw IllegalArgumentException("This RecyclerView works only with a ${LinearLayoutManager::class.java.canonicalName} layout manager class.")
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        if (canScrollVertically(UP)) { // If we can scroll-up then we have scrolled down, so save the scroll state.
            val firstVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            return LinearRecyclerViewState(super.onSaveInstanceState(), firstVisibleItemPosition)
        } else {
            return super.onSaveInstanceState()
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is LinearRecyclerViewState) {
            super.onRestoreInstanceState(state.superState)

            if (state.hasScrolled) {
                positionToScroll = state.firstVisibleItemPosition
            }
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)

        registerAdapterListenerForScrolling(adapter)
    }

    private fun registerAdapterListenerForScrolling(adapter: Adapter<*>?) {
        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (positionToScroll != NO_POSITION && positionToScroll < adapter.itemCount) {
                    placeItemAtTheTop(positionToScroll)
                    positionToScroll = NO_POSITION

                    adapter.unregisterAdapterDataObserver(this)
                }
            }

            private fun placeItemAtTheTop(position: Int) {
                val scroller = object : LinearSmoothScroller(context) {
                    override fun getVerticalSnapPreference(): Int = SNAP_TO_START
                }.apply { targetPosition = position }

                layoutManager?.startSmoothScroll(scroller)
            }
        })
    }
}