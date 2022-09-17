package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.animation.addListener
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0


    var loadingPercentage = 0f

    private val valueAnimator = ValueAnimator.ofFloat(0f, 100f).setDuration(2000).apply {
        addUpdateListener { animator ->
            loadingPercentage = animator.animatedValue as Float
            invalidate()
        }
    }


    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.colorPrimary)
    }


    private var paintLoading = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.colorPrimaryDark)
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.default_text_size)
        textAlign = Paint.Align.CENTER
        color = Color.WHITE
    }

    private var paintCircle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.colorAccent)
    }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

        when (new) {
            ButtonState.Default -> {
                loadingPercentage = 0f
                invalidate()
            }
            ButtonState.Completed -> {
                loadingPercentage = 0f
                valueAnimator.cancel()
                invalidate()
            }

            ButtonState.Loading -> {
                valueAnimator.start()
            }
        }
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)
        canvas?.drawRect(
            0f,
            0f,
            loadingPercentage * widthSize / 100,
            heightSize.toFloat(),
            paintLoading
        )
        canvas?.drawText(
            context.getString(buttonState.text),
            widthSize.toFloat() / 2,
            heightSize.toFloat() / 2 + 15,
            paintText
        )

        if (buttonState == ButtonState.Loading)
            canvas?.drawArc(
                widthSize - 145f,
                heightSize / 2 - 35f,
                widthSize - 75f,
                heightSize / 2 + 35f,
                0F,
                loadingPercentage * 4,
                true,
                paintCircle
            )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    override fun performClick(): Boolean {
        super.performClick()

        invalidate()
        return true
    }

    fun downloadStart() {
        buttonState = ButtonState.Loading
    }
    fun downloadCompleted() {
        buttonState = ButtonState.Completed
        valueAnimator.cancel()
    }
}