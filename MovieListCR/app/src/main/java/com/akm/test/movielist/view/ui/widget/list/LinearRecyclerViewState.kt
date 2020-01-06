package com.akm.test.movielist.view.ui.widget.list

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearRecyclerViewState : View.BaseSavedState {
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<LinearRecyclerViewState> {
            override fun createFromParcel(parcel: Parcel?): LinearRecyclerViewState = LinearRecyclerViewState(parcel)

            override fun newArray(size: Int): Array<LinearRecyclerViewState?> = arrayOfNulls(size)
        }
    }

    val firstVisibleItemPosition: Int

    val hasScrolled: Boolean
        get() = (firstVisibleItemPosition != RecyclerView.NO_POSITION)

    constructor(superState: Parcelable?, firstVisibleItemPosition: Int) : super(superState) {
        this.firstVisibleItemPosition = firstVisibleItemPosition
    }

    private constructor(parcel: Parcel?) : super(parcel) {
        firstVisibleItemPosition = parcel?.readInt() ?: RecyclerView.NO_POSITION
    }

    override fun writeToParcel(out: Parcel?, flags: Int) {
        super.writeToParcel(out, flags)
        out?.writeInt(firstVisibleItemPosition)
    }
}