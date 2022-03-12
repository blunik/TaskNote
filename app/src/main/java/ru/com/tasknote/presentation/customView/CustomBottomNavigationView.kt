package ru.com.tasknote.presentation.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.com.tasknote.R

/**
 * View с нижней навигацией и floatButton
 */
class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    // Рисование элементов
    private val path: Path = Path()
    private val paint: Paint = Paint()

    // Радиус кнопки
    private var radius: Int = 0

    // Координаты для первой кривой
    private val curveFirstStartPoint = Point()
    private val curveFirstEndPoint = Point()
    private val curveFirstControlPoint1 = Point()
    private val curveFirstControlPoint2 = Point()

    // Координаты для второй кривой
    private var curveSecondStartPoint = Point()
    private val curveSecondEndPoint = Point()
    private val curveSecondControlPoint1 = Point()
    private val curveSecondControlPoint2 = Point()

    // Заданнаая ширина и высота
    private var navigationBarWidth: Int = 0
    private var navigationBarHeight: Int = 0

    init {
        // Цвет фона
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Вычисляем радииус кнопки
        radius = width / 20
        // Устанавливаем ширину и высоту
        navigationBarWidth = width
        navigationBarHeight = height

        // (x, y) - начало первой линии
        curveFirstStartPoint.set(
            navigationBarWidth / 2 - radius - radius / 2,
            navigationBarHeight / 4
        )
        // (x, y) - конец первой линии
        curveFirstEndPoint.set(navigationBarWidth / 2, navigationBarHeight / 20)
        // (x, y) - тоже самое для второй линии
        curveSecondStartPoint.set(curveFirstEndPoint.x, curveFirstEndPoint.y)
        curveSecondEndPoint.set(
            navigationBarWidth / 2 + radius + radius / 2,
            curveFirstStartPoint.y
        )

        // (x, y) - для первой точки кривой
        curveFirstControlPoint1.set(
            curveFirstStartPoint.x,
            curveFirstStartPoint.y
        )
        // (x, y) - для второй точки кривой
        curveFirstControlPoint2.set(
            curveFirstEndPoint.x - radius,
            curveFirstEndPoint.y
        )

        // (x, y) - тоже самое для второй кривой
        curveSecondControlPoint1.set(
            curveSecondStartPoint.x + radius,
            curveSecondStartPoint.y
        )
        curveSecondControlPoint2.set(
            curveSecondEndPoint.x,
            curveSecondEndPoint.y
        )

        path.apply {
            reset()
            moveTo(0f, (navigationBarHeight / 4).toFloat())
            lineTo(curveFirstStartPoint.x.toFloat(), curveFirstStartPoint.y.toFloat())

            // Рисование первой кривой
            cubicTo(
                curveFirstControlPoint1.x.toFloat(), curveFirstControlPoint1.y.toFloat(),
                curveFirstControlPoint2.x.toFloat(), curveFirstControlPoint2.y.toFloat(),
                curveFirstEndPoint.x.toFloat(), curveFirstEndPoint.y.toFloat()
            )

            // Рисование второй кривой
            cubicTo(
                curveSecondControlPoint1.x.toFloat(), curveSecondControlPoint1.y.toFloat(),
                curveSecondControlPoint2.x.toFloat(), curveSecondControlPoint2.y.toFloat(),
                curveSecondEndPoint.x.toFloat(), curveSecondEndPoint.y.toFloat()
            )

            lineTo(navigationBarWidth.toFloat(), (navigationBarHeight / 4).toFloat())
            lineTo(navigationBarWidth.toFloat(), navigationBarHeight.toFloat())
            lineTo(0f, navigationBarHeight.toFloat())

            close()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // Заливка объекта
        with(paint) {
            style = Paint.Style.FILL_AND_STROKE
            color = Color.WHITE
        }
        canvas?.drawPath(path, paint)

        // Рисование границ
        with(paint) {
            style = Paint.Style.STROKE
            color = ContextCompat.getColor(context, R.color.grey)
            strokeWidth = 2f
        }
        canvas?.drawPath(path, paint)
    }
}
