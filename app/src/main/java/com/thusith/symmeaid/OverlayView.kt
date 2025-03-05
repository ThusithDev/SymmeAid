package com.thusith.symmeaid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.View

class OverlayView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 8f
        style = Paint.Style.STROKE // Hollow circle
    }

    var faceCenter: PointF? = null
    var faceRadius: Float = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        faceCenter?.let { center ->
            // Draw the circle with calculated center and radius
            canvas.drawCircle(center.x, center.y, faceRadius, paint)
        }
    }
}
