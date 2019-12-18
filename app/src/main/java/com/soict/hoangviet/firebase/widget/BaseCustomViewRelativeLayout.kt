package com.soict.hoangviet.firebase.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.soict.hoangviet.firebase.extension.inflate

abstract class BaseCustomViewRelativeLayout : RelativeLayout {
    protected abstract val layoutRes: Int
    protected open val styleRes: IntArray? = null
    protected lateinit var view: View

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setLayout()
        initListener()
        initView()
        initData()
        attrs?.let {
            initStyleable(it)
        }
    }

    private fun initStyleable(it: AttributeSet) {
        if (styleRes != null && styleRes?.isNotEmpty()!!) {
            val typeArray = context.obtainStyledAttributes(it, styleRes, 0, 0)
            initDataFromStyleable(typeArray)
            typeArray.recycle()
        }
    }

    protected open fun initDataFromStyleable(typeArray: TypedArray) {
    }

    protected open fun initView() {
    }

    protected open fun initData() {
    }

    protected open fun initListener() {
    }

    private fun setLayout() {
        view = context.inflate(layoutRes, this, true)
    }
}