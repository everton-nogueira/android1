package br.com.iesb.radar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.lang.Math.PI
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class Scanner : View {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    private var anguloPonteiro: Float = 0f

    public var angulo: Float
        set(novoAngulo: Float) {
            anguloPonteiro = novoAngulo
            invalidate()
        }
        get() {
            return anguloPonteiro
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val radius = minOf(this.width / 2, this.height).toFloat()
        val center = PointF(this.width.toFloat() / 2, this.height.toFloat())
        this.setBackgroundColor(Color.BLACK)
        desenhaRadar(center, radius, canvas)
        desenhaPonteiro(center, radius, anguloPonteiro, canvas)
    }

    private fun desenhaRadar(centro: PointF, raio: Float, canvas: Canvas) {
        val slice = 16
        var paint = Paint()
        paint.strokeWidth = 3f
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL_AND_STROKE

        desenhaGrid(centro, raio, paint, canvas)

        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.textSize = 30f

        desenhaGrid(centro, raio, paint, canvas)

        paint.strokeWidth = 1f


        for (dist in 1..slice) {
            val distRadius = dist.toFloat() * raio / slice
            desenhaGrid(centro, distRadius, paint, canvas)
        }

        for (arc in 1..slice) {
            val angleArc: Float = PI.toFloat() + arc.toFloat() * PI.toFloat() / slice.toFloat()
            val x = centro.x + raio * cos(angleArc)
            val y = centro.y + raio * sin(angleArc)
            val arcPoint = PointF(x, y)
            canvas.drawLine(centro.x, centro.y, arcPoint.x, arcPoint.y, paint)
            canvas.drawText("${-90 + ((angleArc - PI) * 180 / PI).roundToInt()}º", arcPoint.x - if (cos(angleArc) < 0f) {
                40f
            } else {
                0f
            }, arcPoint.y, paint)
        }
    }

    private fun desenhaGrid(centro: PointF, raio: Float, paint: Paint, canvas: Canvas) {
        val esquerda = centro.x - raio
        val topo = centro.y - raio
        val direita = centro.x + raio
        val bottom = centro.y + raio
        val rectGridArc = RectF(esquerda, topo, direita, bottom)
        canvas.drawArc(rectGridArc, 180f, 360f, true, paint)
    }

    private fun desenhaPonteiro(centro: PointF, raio: Float, anguloPonteiro: Float, canvas: Canvas, perda: Int = 8) {
        if (perda == 0) {
            return
        }

        var paint = Paint()
        paint.strokeWidth = 10f
        paint.color = Color.GREEN
        paint.alpha = 32 * perda - 1
        paint.style = Paint.Style.STROKE

        val anguloPonteiro = PI + anguloPonteiro - ((8 - perda) * PI / 120).toFloat()
        val x = centro.x + raio * cos(anguloPonteiro).toFloat()
        val y = centro.y - raio * sin(anguloPonteiro).absoluteValue.toFloat()
        val arcPoint = PointF(x, y)
        canvas.drawLine(centro.x, centro.y, arcPoint.x, arcPoint.y, paint)
    }

}