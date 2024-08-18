package com.tharani.symmeaid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour

class OverlayView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.RED
        strokeWidth = 8f
    }

    var faceMeshPoints: List<PointF> = emptyList()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        faceMeshPoints.forEach { point ->
            canvas.drawCircle(point.x, point.y, 8f, paint)
        }
    }
}
