package com.akm.test.movielist.view.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircularPercentageView : View {
    private companion object {
        const val VIOLET = 0xEE82EE
        const val INDIGO = 0x4B0082
        const val ORANGE = 0xFFA500
        val COLORS = arrayOf(VIOLET, INDIGO, Color.BLUE, Color.GREEN, Color.YELLOW, ORANGE, Color.RED)
        val COLOR_SEGMENT_WIDTH = 100.0/COLORS.size

        const val ACTIVE_ALPHA = 0xFF
        const val INACTIVE_ALPHA = 0xFF/10

        val FILL_STYLE = Paint.Style.FILL

        const val NORTH_DEG = 270f
        const val CIRCLE_DEG = 360
        const val PERCENTAGE_TO_DEG = 3.6f
        const val RING_RADIUS_FRACTION = 0.17f
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

    private val backgroundPaint = Paint().apply {
        //TODO Background colour as an input parameter.
        color = ContextCompat.getColor(context, android.R.color.white)
        style = FILL_STYLE
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

            val cx = width/2f
            val cy = height/2f
            val radius = Math.min(width, height)/2f

            val left = cx - radius
            val top = cy - radius
            val right = cx + radius
            val bottom = cy + radius

            val sweepAngleActiveDeg = PERCENTAGE_TO_DEG*percentage
            val sweepAngleInactiveDeg = CIRCLE_DEG - sweepAngleActiveDeg

            val startAngleActiveDeg = NORTH_DEG
            val startAngleInactiveDeg = startAngleActiveDeg - sweepAngleInactiveDeg

            val delta = radius*RING_RADIUS_FRACTION
            val leftInner = left + delta
            val topInner = top + delta
            val rightInner = right - delta
            val bottomInner = bottom - delta

            // https://thoughtbot.com/blog/android-canvas-drawarc-method-a-visual-guide
            canvas.drawArc(left, top, right, bottom, startAngleActiveDeg, sweepAngleActiveDeg, true, activePaint)
            canvas.drawArc(left, top, right, bottom, startAngleInactiveDeg, sweepAngleInactiveDeg, true, inactivePaint)

            canvas.drawArc(leftInner, topInner, rightInner, bottomInner, startAngleActiveDeg, sweepAngleActiveDeg, true, backgroundPaint)
            canvas.drawArc(leftInner, topInner, rightInner, bottomInner, startAngleInactiveDeg, sweepAngleInactiveDeg, true, backgroundPaint)
        }
    }
}