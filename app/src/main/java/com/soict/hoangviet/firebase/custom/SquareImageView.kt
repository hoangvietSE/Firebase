package com.soict.hoangviet.firebase.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class SquareImageView : androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidth = measuredWidth
        val measureHeight = measuredHeight
        if (measureWidth > measureHeight) {
            setMeasuredDimension(measureHeight, measureHeight)
        } else {
            setMeasuredDimension(measureWidth, measureWidth)
        }
    }
}