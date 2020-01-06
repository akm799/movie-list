package com.akm.test.movielist.view.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PercentageView : View {
    private companion object {
        const val VIOLET = 0xEE82EE
        const val INDIGO = 0x4B0082
        const val ORANGE = 0xFFA500
        val COLORS = arrayOf(VIOLET, INDIGO, Color.BLUE, Color.GREEN, Color.YELLOW, ORANGE, Color.RED)
        val COLOR_SEGMENT_WIDTH = 100.0/COLORS.size

        const val ACTIVE_ALPHA = 0xFF
        const val INACTIVE_ALPHA = 0xFF/10

        val FILL_STYLE = Paint.Style.FILL
    }

    private val activePaints = COLORS.map {
        Paint().apply {
            color = it
            style = FILL_STYLE
            alpha = ACTIVE_ALPHA
        }
    }

    private val inactivePaints = COLORS.map {
        Paint().apply {
            color = it
            style = FILL_STYLE
            alpha = INACTIVE_ALPHA
        }
    }

    private var percentage: Int = 0

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setPercentage(percentage: Int) {
        if (percentage in 0..100) {
            this.percentage = percentage
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            val paintIndex = if (percentage < 100) Math.floor(percentage/COLOR_SEGMENT_WIDTH).toInt() else COLORS.size - 1
            val activePaint = activePaints[paintIndex]
            val inactivePaint = inactivePaints[paintIndex]

            val divider = percentage*width/100f
            canvas.drawRect(0f, 0f, divider, height.toFloat(), activePaint)
            canvas.drawRect(divider, 0f, width.toFloat(), height.toFloat(), inactivePaint)
        }
    }
}